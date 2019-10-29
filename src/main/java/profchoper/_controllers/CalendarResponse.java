package profchoper._controllers;

// Data Transfer Object for calendar
public class CalendarResponse {
    private String status;
    private Object data;

    public CalendarResponse() {
        // default constructor
    }

    public CalendarResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
