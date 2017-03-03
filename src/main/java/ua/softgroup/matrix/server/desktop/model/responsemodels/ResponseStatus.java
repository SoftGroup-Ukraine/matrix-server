package ua.softgroup.matrix.server.desktop.model.responsemodels;

/**
 * @author Vadim Boitsov <sg.vadimbojcov@gmail.com>
 */
public enum ResponseStatus {
    SUCCESS,
    FAIL,
    INVALID_CREDENTIALS,
    REPORT_EXISTS,
    INVALID_TOKEN,
    REPORT_EXPIRED;
}
