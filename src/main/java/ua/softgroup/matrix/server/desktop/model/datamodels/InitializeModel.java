package ua.softgroup.matrix.server.desktop.model.datamodels;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Vadim Boitsov <sg.vadimbojcov@gmail.com>
 */
public class InitializeModel implements Serializable, DataModel {
    private static final long serialVersionUID = 1L;

    private String token;

    private Set<ProjectModel> projectModels;

    private int idlePeriod;

    private int screenshotFrequency;

    private int checkPointFrequency;

    public InitializeModel() {
    }

    public InitializeModel(String token, Set<ProjectModel> projectModels, int idlePeriod, int screenshotFrequency, int checkPointFrequency) {
        this.token = token;
        this.projectModels = projectModels;
        this.idlePeriod = idlePeriod;
        this.screenshotFrequency = screenshotFrequency;
        this.checkPointFrequency = checkPointFrequency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<ProjectModel> getProjectModels() {
        return projectModels;
    }

    public void setProjectModels(Set<ProjectModel> projectModels) {
        this.projectModels = projectModels;
    }

    public int getIdlePeriod() {
        return idlePeriod;
    }

    public void setIdlePeriod(int idlePeriod) {
        this.idlePeriod = idlePeriod;
    }

    public int getScreenshotFrequency() {
        return screenshotFrequency;
    }

    public void setScreenshotFrequency(int screenshotFrequency) {
        this.screenshotFrequency = screenshotFrequency;
    }

    public int getCheckPointFrequency() {
        return checkPointFrequency;
    }

    public void setCheckPointFrequency(int checkPointFrequency) {
        this.checkPointFrequency = checkPointFrequency;
    }

    @Override
    public String toString() {
        return "InitializeModel{" +
                "token='" + token + '\'' +
                ", projectModels=" + projectModels +
                ", idlePeriod=" + idlePeriod +
                ", screenshotFrequency=" + screenshotFrequency +
                ", checkPointFrequency=" + checkPointFrequency +
                '}';
    }
}
