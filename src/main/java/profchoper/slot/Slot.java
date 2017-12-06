package profchoper.slot;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static profchoper._misc.Constant.*;

public class Slot {
    private final Timestamp timestamp;
    private final LocalDateTime startDateTime;
    private final DayOfWeek dayOfWeek;
    private final String profAlias;
    private Integer studentId = null;
    private String bookStatus = AVAIL;

    public Slot(String profAlias, Timestamp startTimestamp) {
        this.profAlias = profAlias;
        this.timestamp = startTimestamp;
        this.startDateTime = startTimestamp.toLocalDateTime();
        this.dayOfWeek = this.startDateTime.getDayOfWeek();
    }

    // For inserting into database
    public Slot(String profAlias, LocalDateTime startDateTime) throws SlotException {
        LocalTime startTime = startDateTime.toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new SlotException("Start time must be within set boundaries.");

        this.profAlias = profAlias;
        this.timestamp = Timestamp.valueOf(startDateTime);
        this.startDateTime = startDateTime;
        this.dayOfWeek = this.startDateTime.getDayOfWeek();
    }

    public void book(Integer studentId) {
        bookStatus = PENDING;
        this.studentId = studentId;
    }

    public void cancel() {
        bookStatus = AVAIL;
        this.studentId = null;
    }

    public void confirm() {
        bookStatus = BOOKED;
    }

    @Override
    // Slots are considered equal if they have the same dayOfWeek and startTime
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;

        Slot comparedSlot = (Slot) obj;

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

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getProfAlias() {
        return profAlias;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getBookStatus() {
        return bookStatus;
    }
}
