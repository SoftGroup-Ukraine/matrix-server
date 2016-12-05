package ua.softgroup.matrix.server.supervisor.jersey.json;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
 */
public class SummaryJson {

    private LocalDate date;
    private Long workTimeMinutes;
    private Long idleTimeMinutes;
    private Integer rate;
    private Integer currencyId;
    private boolean checked;
    private Double coefficient;
    private LocalDateTime start;
    private LocalDateTime end;

    public SummaryJson() {
    }

    public SummaryJson(LocalDate date, Long workTimeMinutes, Long idleTimeMinutes, Integer rate, Integer currencyId,
                       boolean checked, Double coefficient, LocalDateTime start, LocalDateTime end) {
        this.date = date;
        this.workTimeMinutes = workTimeMinutes;
        this.idleTimeMinutes = idleTimeMinutes;
        this.rate = rate;
        this.currencyId = currencyId;
        this.checked = checked;
        this.coefficient = coefficient;
        this.start = start;
        this.end = end;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getWorkTimeMinutes() {
        return workTimeMinutes;
    }

    public void setWorkTimeMinutes(Long workTimeMinutes) {
        this.workTimeMinutes = workTimeMinutes;
    }

    public Long getIdleTimeMinutes() {
        return idleTimeMinutes;
    }

    public void setIdleTimeMinutes(Long idleTimeMinutes) {
        this.idleTimeMinutes = idleTimeMinutes;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "SummaryJson{" +
                "date=" + date +
                ", workTimeMinutes=" + workTimeMinutes +
                ", idleTimeMinutes=" + idleTimeMinutes +
                ", rate=" + rate +
                ", currencyId=" + currencyId +
                ", checked=" + checked +
                ", coefficient=" + coefficient +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}