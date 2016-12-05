package ua.softgroup.matrix.server.persistent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ua.softgroup.matrix.server.supervisor.jersey.json.JsonViewType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonView(JsonViewType.OUT.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(JsonViewType.OUT.class)
    @Column
    @CreationTimestamp
    private LocalDateTime creationDate;

    @JsonView(JsonViewType.OUT.class)
    @Column
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @JsonView({ JsonViewType.OUT.class, JsonViewType.IN.class })
    @Column
    private String title;

    @JsonView({ JsonViewType.OUT.class, JsonViewType.IN.class })
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workDay_id")
    private WorkDay workDay;

    @JsonIgnore
    @ManyToOne
    private User author;

    @JsonIgnore
    @ManyToOne
    private Project project;


    public Report() {
    }

    public Report(Long id) {
        this.id = id;
    }

    public Report(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Report(String title, String description, Project project, User author) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
