package profchoper.bookingSlot;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static profchoper._misc.Constant.*;

public class BookingSlot {
    private final Timestamp timestamp;
    private final String profAlias;
    private Integer studentId = null;
    private String bookStatus = AVAIL;

    public BookingSlot(String profAlias, Timestamp startTimestamp) {
        this.profAlias = profAlias;
        this.timestamp = startTimestamp;
    }

    // For inserting into database
    public BookingSlot(String profAlias, LocalDateTime startDateTime) throws BookingSlotException {
        LocalTime startTime = startDateTime.toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new BookingSlotException("Start time must be within set boundaries.");

        this.profAlias = profAlias;
        this.timestamp = Timestamp.valueOf(startDateTime);
    }

    public LocalDateTime getDateTime() {
        return timestamp.toLocalDateTime();
    }

    public DayOfWeek getDayOfWeek() {
        return timestamp.toLocalDateTime().getDayOfWeek();
    }

    public LocalDate getDate() {
        return timestamp.toLocalDateTime().toLocalDate();
    }

    public LocalTime getTime() {
        return timestamp.toLocalDateTime().toLocalTime();
    }


    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    // Slots are considered equal if they have the same dayOfWeek and startTime
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;

        BookingSlot comparedSlot = (BookingSlot) obj;

        if (!comparedSlot.timestamp.equals(this.timestamp))
            return false;

        if (!comparedSlot.profAlias.equals(this.profAlias))
            return false;

        return true;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getProfAlias() {
        return profAlias;
    }

    public String getBookStatus() {
        return bookStatus;
    }
}
