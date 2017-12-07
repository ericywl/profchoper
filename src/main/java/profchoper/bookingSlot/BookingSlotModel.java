package profchoper.bookingSlot;

import profchoper.professor.Professor;
import profchoper.student.Student;

import java.sql.Timestamp;

import static profchoper._misc.Constant.AVAIL;

public class BookingSlotModel {
    private final Timestamp timestamp;
    private final String profAlias;
    private Integer StudentId = null;
    private String bookStatus = AVAIL;

    public BookingSlotModel(String profAlias, Timestamp startTimestamp) {
        this.profAlias = profAlias;
        this.timestamp = startTimestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getProfAlias() {
        return profAlias;
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
