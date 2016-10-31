package ua.softgroup.matrix.server.persistent.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="Report1")
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private boolean checked;

    @ManyToOne
    private User checker;

    @ManyToOne
    private User author;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
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
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
