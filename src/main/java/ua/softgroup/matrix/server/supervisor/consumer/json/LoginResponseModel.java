package ua.softgroup.matrix.server.supervisor.consumer.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Vadim on 24.10.2016.
 */
public class LoginResponseModel implements RetrofitModel {

    @JsonProperty("success") private Boolean success;
    @JsonProperty("message") private String message;
    @JsonProperty("tracker-token") private String trackerToken;
    @JsonProperty("user") private UserModel userModel;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrackerToken() {
        return trackerToken;
    }

    public void setTrackerToken(String trackerToken) {
        this.trackerToken = trackerToken;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "success=" + success +
                ", trackerToken='" + trackerToken + '\'' +
                ", userModel=" + userModel +
                '}';
    }
}