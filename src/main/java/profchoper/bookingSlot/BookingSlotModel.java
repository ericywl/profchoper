package profchoper.bookingSlot;

import java.sql.Timestamp;

import static profchoper._misc.Constant.AVAIL;

public class BookingSlotModel {
    private Timestamp timestamp;
    private String profAlias;
    private Integer StudentId = null;
    private String bookStatus = AVAIL;

    public BookingSlotModel() {
        // empty constructor
    }

    public BookingSlotModel(String profAlias, Timestamp startTimestamp) {
        this.profAlias = profAlias;
        this.timestamp = startTimestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfAlias() {
        return profAlias;
    }

    public void setProfAlias(String profAlias) {
        this.profAlias = profAlias;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setStudentId(Integer studentId) {
        StudentId = studentId;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
