package ua.softgroup.matrix.server.supervisor.producer.json.v2;

/**
 * @author Oleksandr Tyshkovets <sg.olexander@gmail.com>
 */
public class Period {

    private String start;
    private String end;
    private int workTimeSeconds;
    private int idleTimeSeconds;
    private double idlePercentage;
    private long entityId;
    private String entityType= "project";
    private int rate;
    private int currencyId;

    public Period() {
    }

    public Period(String start, String end, int workTimeSeconds, int idleTimeSeconds, double idlePercentage,
                  long entityId, int rate, int currencyId) {
        this.start = start;
        this.end = end;
        this.workTimeSeconds = workTimeSeconds;
        this.idleTimeSeconds = idleTimeSeconds;
        this.idlePercentage = idlePercentage;
        this.entityId = entityId;
        this.rate = rate;
        this.currencyId = currencyId;
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

    public int getWorkTimeSeconds() {
        return workTimeSeconds;
    }

    public void setWorkTimeSeconds(int workTimeSeconds) {
        this.workTimeSeconds = workTimeSeconds;
    }

    public int getIdleTimeSeconds() {
        return idleTimeSeconds;
    }

    public void setIdleTimeSeconds(int idleTimeSeconds) {
        this.idleTimeSeconds = idleTimeSeconds;
    }

    public double getIdlePercentage() {
        return idlePercentage;
    }

    public void setIdlePercentage(double idlePercentage) {
        this.idlePercentage = idlePercentage;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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
}
