package ua.softgroup.matrix.server.desktop.model.datamodels;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Vadim Boitsov <sg.vadimbojcov@gmail.com>
 */
public class TimeModel implements Serializable, DataModel {
    private static final long serialVersionUID = 1L;

    private int totalTime;

    private int todayTime;

    private LocalDateTime todayStartTime;

    private double idlePercent;

    public TimeModel(int totalTime, int todayTime, double idlePercent) {
        this.totalTime = totalTime;
        this.todayTime = todayTime;
        this.idlePercent = idlePercent;
    }

    public TimeModel(int totalTime, int todayTime, LocalDateTime todayStartTime, double idlePercent) {
        this.totalTime = totalTime;
        this.todayTime = todayTime;
        this.todayStartTime = todayStartTime;
        this.idlePercent = idlePercent;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(int todayTime) {
        this.todayTime = todayTime;
    }

    public LocalDateTime getTodayStartTime() {
        return todayStartTime;
    }

    public void setTodayStartTime(LocalDateTime todayStartTime) {
        this.todayStartTime = todayStartTime;
    }

    public double getIdlePercent() {
        return idlePercent;
    }

    public void setIdlePercent(double idlePercent) {
        this.idlePercent = idlePercent;
    }
}
