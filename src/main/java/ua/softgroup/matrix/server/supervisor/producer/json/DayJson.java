package ua.softgroup.matrix.server.supervisor.producer.json;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
 */
public class DayJson {

    private long id;
    private String entityType = "project"; //TODO use enum
    private long projectId;
    private LocalDate date;
    private String start;
    private String end;
    private int workSeconds;
    private int idleSeconds;
    private double idlePercentage;
    private boolean checked;
    private long checkerId;
    private double coefficient = 1.0f;
    private String reportText = "";
    private int rate;
    private int currencyId;
    private Set<WorkPeriod> workPeriods;

    public DayJson() {
    }

    public DayJson(long id, long projectId, LocalDate date, String start, String end, int workSeconds, int idleSeconds,
                   double idlePercentage, boolean checked, long checkerId, double coefficient, String reportText,
                   int rate, int currencyId, Set<WorkPeriod> workPeriods) {
        this.id = id;
        this.projectId = projectId;
        this.date = date;
        this.start = start;
        this.end = end;
        this.workSeconds = workSeconds;
        this.idleSeconds = idleSeconds;
        this.idlePercentage = idlePercentage;
        this.checked = checked;
        this.checkerId = checkerId;
        this.coefficient = coefficient;
        this.reportText = reportText;
        this.rate = rate;
        this.currencyId = currencyId;
        this.workPeriods = workPeriods;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getWorkSeconds() {
        return workSeconds;
    }

    public void setWorkSeconds(int workSeconds) {
        this.workSeconds = workSeconds;
    }

    public int getIdleSeconds() {
        return idleSeconds;
    }

    public void setIdleSeconds(int idleSeconds) {
        this.idleSeconds = idleSeconds;
    }

    public double getIdlePercentage() {
        return idlePercentage;
    }

    public void setIdlePercentage(double idlePercentage) {
        this.idlePercentage = idlePercentage;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(long checkerId) {
        this.checkerId = checkerId;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public Set<WorkPeriod> getWorkPeriods() {
        return workPeriods;
    }

    public void setWorkPeriods(Set<WorkPeriod> workPeriods) {
        this.workPeriods = workPeriods;
    }
}
