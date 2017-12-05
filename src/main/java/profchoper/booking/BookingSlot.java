package profchoper.booking;

import profchoper._misc.Constant;
import profchoper.user.Professor;
import profchoper.user.Student;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static profchoper._misc.Constant.*;

public class BookingSlot {
    private final Timestamp timestamp;
    private final LocalDate date;
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Professor professor;
    private String bookStatus = Constant.AVAIL;
    private Student student = null;

    public BookingSlot(Professor professor, Timestamp startTimestamp) throws BookingSlotException {
        LocalTime startTime = startTimestamp.toLocalDateTime().toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new BookingSlotException("Start time must be within set boundaries.");

        this.professor = professor;
        this.timestamp = startTimestamp;
        this.date = startTimestamp.toLocalDateTime().toLocalDate();
        this.day = this.date.getDayOfWeek();
        this.startTime = startTime;
        this.endTime = this.startTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
    }

    public BookingSlot(Professor professor, LocalDateTime startDateTime) throws BookingSlotException {
        LocalTime startTime = startDateTime.toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new BookingSlotException("Start time must be within set boundaries.");

        this.professor = professor;
        this.timestamp = Timestamp.valueOf(startDateTime);
        this.date = startDateTime.toLocalDate();
        this.day = this.date.getDayOfWeek();
        this.startTime = startDateTime.toLocalTime();
        this.endTime = this.startTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
    }

    public void book(Student student) {
        bookStatus = PENDING;
        this.student = student;
    }

    public void cancel() {
        bookStatus = Constant.AVAIL;
        this.student = null;
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

        BookingSlot comparedSlot = (BookingSlot) obj;

        if (!comparedSlot.timestamp.equals(this.timestamp))
            return false;

        if (!comparedSlot.professor.equals(this.professor))
            return false;

        return true;
    }

    public Professor getProfessor() {
        return professor;
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

    public Student getStudent() {
        return student;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
