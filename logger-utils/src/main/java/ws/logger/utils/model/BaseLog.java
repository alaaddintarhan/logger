package ws.logger.utils.model;

import java.util.Date;

public class BaseLog {

    private String appName;
    private String currentServiceHost;
    private Date requestDate;
    private String responseData;
    private StringBuilder requestDataBuilder;

    public BaseLog() {
        requestDataBuilder = new StringBuilder();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCurrentServiceHost() {
        return currentServiceHost;
    }

    public void setCurrentServiceHost(String currentServiceHost) {
        this.currentServiceHost = currentServiceHost;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getRequestData() {
        return requestDataBuilder.toString();
    }

    public void appendRequestData(String data) {
        requestDataBuilder.append(data).append("\n");
    }


}