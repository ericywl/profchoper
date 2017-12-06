package profchoper.slot;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static profchoper._misc.Constant.*;

public class Slot {
    private final Timestamp timestamp;
    private final LocalDate date;
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String profAlias;
    private Integer studentId = null;
    private String bookStatus = AVAIL;

    public Slot(String profAlias, Timestamp startTimestamp) {
        LocalTime startTime = startTimestamp.toLocalDateTime().toLocalTime();

        this.profAlias = profAlias;
        this.timestamp = startTimestamp;
        this.date = startTimestamp.toLocalDateTime().toLocalDate();
        this.day = this.date.getDayOfWeek();
        this.startTime = startTime;
        this.endTime = this.startTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
    }

    // For inserting into database
    public Slot(String profAlias, LocalDateTime startDateTime) throws SlotException {
        LocalTime startTime = startDateTime.toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new SlotException("Start time must be within set boundaries.");

        this.profAlias = profAlias;
        this.timestamp = Timestamp.valueOf(startDateTime);
        this.date = startDateTime.toLocalDate();
        this.day = this.date.getDayOfWeek();
        this.startTime = startDateTime.toLocalTime();
        this.endTime = this.startTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
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
    public String toString() {
        return date + ": " + startTime + " => " + endTime;
    }

    @Override
    // Slots are considered equal if they have the same day and startTime
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
