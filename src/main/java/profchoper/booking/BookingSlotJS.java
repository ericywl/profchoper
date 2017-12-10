package profchoper.booking;


import javax.validation.constraints.NotNull;

public class BookingSlotJS {
    @NotNull
    private String profAlias;

    private String time;

    public BookingSlotJS() {
        // default constructor
    }

    public BookingSlotJS(String profAlias, String time) {
        this.profAlias = profAlias;
        this.time = time;
    }

    public String getProfAlias() {
        return profAlias;
    }

    public void setProfAlias(String profAlias) {
        this.profAlias = profAlias;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
