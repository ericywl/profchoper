package profchoper.bookingSlot;

import profchoper.professor.Professor;
import profchoper.student.Student;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static profchoper._misc.Constant.*;

public class BookingSlot {
    private final Timestamp timestamp;
    private final Professor professor;
    private Student student = null;
    private String bookStatus = AVAIL;

    public BookingSlot(Professor professor, Timestamp startTimestamp) {
        this.professor = professor;
        this.timestamp = startTimestamp;
    }

    public BookingSlot(Timestamp timestamp, Professor professor, Student student, String bookStatus) {
        this.timestamp = timestamp;
        this.professor = professor;
        this.student = student;
        this.bookStatus = bookStatus;
    }

    // For inserting into database
    public BookingSlot(Professor professor, LocalDateTime startDateTime) throws BookingSlotException {
        LocalTime startTime = startDateTime.toLocalTime();
        if (startTime.isBefore(DAY_FIRST_START_TIME) || startTime.isAfter(DAY_LAST_START_TIME))
            throw new BookingSlotException("Start time must be within set boundaries.");

        this.professor = professor;
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

    @Override
    // Slots are considered equal if they have the same dayOfWeek and startTime
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

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Student getStudent() {
        return student;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    public Professor getProfessor() {
        return professor;
    }

    public String getBookStatus() {
        return bookStatus;
    }
}
