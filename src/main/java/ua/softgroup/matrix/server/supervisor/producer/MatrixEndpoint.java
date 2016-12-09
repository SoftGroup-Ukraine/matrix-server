package ua.softgroup.matrix.server.supervisor.producer;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softgroup.matrix.server.persistent.entity.AbstractPeriod;
import ua.softgroup.matrix.server.persistent.entity.Project;
import ua.softgroup.matrix.server.persistent.entity.Report;
import ua.softgroup.matrix.server.persistent.entity.TimeAudit;
import ua.softgroup.matrix.server.persistent.entity.User;
import ua.softgroup.matrix.server.persistent.entity.WorkDay;
import ua.softgroup.matrix.server.persistent.entity.WorkTime;
import ua.softgroup.matrix.server.persistent.repository.TimeAuditRepository;
import ua.softgroup.matrix.server.persistent.repository.WorkDayRepository;
import ua.softgroup.matrix.server.service.ProjectService;
import ua.softgroup.matrix.server.service.ReportService;
import ua.softgroup.matrix.server.service.UserService;
import ua.softgroup.matrix.server.service.WorkTimeService;
import ua.softgroup.matrix.server.supervisor.producer.json.ErrorJson;
import ua.softgroup.matrix.server.supervisor.producer.json.JsonViewType;
import ua.softgroup.matrix.server.supervisor.producer.json.ReportJson;
import ua.softgroup.matrix.server.supervisor.producer.json.SummaryJson;
import ua.softgroup.matrix.server.supervisor.producer.json.TimeJson;
import ua.softgroup.matrix.server.supervisor.producer.token.TokenHelper;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
 */
@Component
@Path("/")
@Api("Matrix Endpoint")
public class MatrixEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(MatrixEndpoint.class);

    private final ReportService reportService;
    private final ProjectService projectService;
    private final UserService userService;
    private final WorkTimeService workTimeService;

    @Autowired
    private TimeAuditRepository timeAuditRepository;
    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    public MatrixEndpoint(ReportService reportService, ProjectService projectService, UserService userService, WorkTimeService workTimeService) {
        this.reportService = reportService;
        this.projectService = projectService;
        this.userService = userService;
        this.workTimeService = workTimeService;
    }

    @GET
    @Path("/users/{username}/{project_id}/reports")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(JsonViewType.OUT.class)
    @ApiOperation(
            value = "Returns reports of the user's project",
            response = ReportJson.class,
            responseContainer = "List"
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When project id <= 0", response = ErrorJson.class)
    })
    public Response getReportsOf(@PathParam("username") String username,
                                 @Min(0) @PathParam("project_id") Long projectId) {

        Project project = projectService.getById(projectId).orElseThrow(NotFoundException::new);
        User user = userService.getByUsername(username).orElseThrow(NotFoundException::new);
        List<ReportJson> reports = reportService.getAllReportsOf(user, project).stream()
                .map(reportService::convertEntityToJson)
                .collect(Collectors.toList());
        return Response.ok(reports).build();
    }

    @GET
    @Path("/users/{username}/{project_id}/summary")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @ApiOperation(
            value = "Returns a daily summary of current month for the user's project",
            response = SummaryJson.class,
            responseContainer = "List"
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When project id <= 0", response = ErrorJson.class)
    })
    public Response getReportsOf(@HeaderParam("token") String token,
                                 @PathParam("username") String username,
                                 @Min(0) @PathParam("project_id") Long projectId) {

        Project project = projectService.getById(projectId).orElseThrow(NotFoundException::new);
        User user = userService.getByUsername(username).orElseThrow(NotFoundException::new);
        WorkTime workTime = workTimeService.getWorkTimeOfUserAndProject(user, project).orElseThrow(NotFoundException::new);

        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        List<SummaryJson> summary = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, LocalDate.now().plusDays(1)))
                .map(localDate -> workDayRepository.findByDateAndWorkTime(localDate, workTime))
                .filter(Objects::nonNull)
                .map(workDay -> createSummaryJson(workTime, workDay))
                .collect(Collectors.toList());
        return Response.ok(summary).build();
    }

    private SummaryJson createSummaryJson(WorkTime workTime, WorkDay workDay) {
        return new SummaryJson(
                workDay.getDate(),
                workDay.getWorkMinutes(),
                workDay.getIdleMinutes(),
                workTime.getRate(),
                workTime.getRateCurrencyId(),
                workDay.isChecked(),
                workDay.getCoefficient(),
                workDay.getWorkTimePeriods().stream()
                        .map(AbstractPeriod::getStart)
                        .min(LocalDateTime::compareTo)
                        .orElse(null),
                workDay.getWorkTimePeriods().stream()
                        .map(AbstractPeriod::getEnd)
                        .max(LocalDateTime::compareTo)
                        .orElse(null)
        );

    }

    @GET
    @Path("/users/{username}/{project_id}/time")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Returns a today/total work time of the user's project",
            response = TimeJson.class
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When project id <= 0", response = ErrorJson.class)
    })
    public Response getTotalTime(@PathParam("username") String username,
                                 @Min(0) @PathParam("project_id") Long projectId) {

        Project project = projectService.getById(projectId).orElseThrow(NotFoundException::new);
        User user = userService.getByUsername(username).orElseThrow(NotFoundException::new);
        WorkTime workTime = workTimeService.getWorkTimeOfUserAndProject(user, project).orElse(new WorkTime(0L, 0L, project, user));
        return Response.ok(new TimeJson(workTime.getTodayMinutes(), workTime.getTotalMinutes())).build();
    }

    @POST
    @Path("/users/{username}/{project_id}/time")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(JsonViewType.OUT.class)
    @ApiOperation(
            value = "Add a work time for the user's project",
            response = TimeJson.class
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When project id <= 0", response = ErrorJson.class)
    })
    public Response addTime(@HeaderParam("token") String token,
                            @PathParam("username") String username,
                            @PathParam("project_id") @Min(0) Long projectId,
                            @JsonView(JsonViewType.IN.class) TimeJson timeJson) {

        LOG.info("POST JSON {}", timeJson);
        Project project = projectService.getById(projectId).orElseThrow(NotFoundException::new);
        User user = userService.getByUsername(username).orElseThrow(NotFoundException::new);
        WorkTime workTime = workTimeService.getWorkTimeOfUserAndProject(user, project).orElse(new WorkTime(0L, 0L, project, user));
        workTime.setTotalMinutes(workTime.getTotalMinutes() + timeJson.getTotalMinutes());
        workTimeService.save(workTime);

        WorkDay workDay = Optional.ofNullable(workDayRepository.findByDateAndWorkTime(timeJson.getDate(), workTime))
                                  .orElse(new WorkDay(0L, 0L, workTime));
        workDay.setWorkMinutes(workDay.getWorkMinutes() + timeJson.getTotalMinutes());
        workDayRepository.save(workDay);

        User principal = userService.getByUsername(TokenHelper.extractSubjectFromToken(token)).orElseThrow(NotFoundException::new);
        timeAuditRepository.save(new TimeAudit(timeJson.getTotalMinutes(), timeJson.getReason(), principal, workDay));

        return Response.ok(new TimeJson(workTime.getTodayMinutes(), workTime.getTotalMinutes())).build();
    }

    @PUT
    @Path("/reports/{report_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a report by id",
            response = ReportJson.class
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When report id <= 0", response = ErrorJson.class)
    })
    public Response updateReport(@Min(0) @PathParam("report_id") Long reportId,
                                 @JsonView(JsonViewType.IN.class) ReportJson reportJson) {
        LOG.info("PUT JSON {}", reportJson);
        Report report = reportService.getById(reportId).orElseThrow(NotFoundException::new);
        report.setTitle(reportJson.getTitle());
        report.setDescription(reportJson.getDescription());
        return Response.ok(reportService.convertEntityToJson(reportService.save(report))).build();
    }

    @POST
    @Path("/reports/{report_id}/check")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(JsonViewType.OUT.class)
    @ApiOperation(
            value = "Checks a report and set coefficient",
            response = ReportJson.class
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "When report id <= 0 or coefficient <= 0", response = ErrorJson.class),
    })
    public Response checkReport(@HeaderParam("token") String token,
                                @Min(0) @PathParam("report_id") Long reportId,
                                @NotNull @DecimalMin(value = "0") @FormParam("coefficient") Double coefficient) {

        Report report = reportService.getById(reportId).orElseThrow(NotFoundException::new);
        //TODO retrieve principal in token auth filter
        User user = userService.getByUsername(TokenHelper.extractSubjectFromToken(token)).orElseThrow(NotFoundException::new);
        report.getWorkDay().setChecker(user);
        report.getWorkDay().setChecked(true);
        report.getWorkDay().setCoefficient(coefficient);
        workDayRepository.save(report.getWorkDay());
        return Response.ok(reportService.convertEntityToJson(reportService.save(report))).build();
    }


}
