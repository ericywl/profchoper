package profchoper.booking;


import profchoper.professor.Professor;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static profchoper._config.Constant.AVAIL;


public class BookingSlot {
    private Timestamp timestamp;
    private Professor professor;
    private Integer studentId = null;
    private String studentName = null;
    private String bookStatus = AVAIL;

    public BookingSlot() {
        // default constructor
    }

    public BookingSlot(Professor professor, Timestamp startTimestamp) {
        this.professor = professor;
        this.timestamp = startTimestamp;
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

    public LocalTime getStartTime() {
        return timestamp.toLocalDateTime().toLocalTime();
    }

    public LocalTime getEndTime() {
        return getStartTime().plus(30, ChronoUnit.MINUTES);
    }

    // Prof's office location
    public String getLocation() {
        return professor.getOffice();
    }

    // For showing on student page
    public String toStringStudent() {
        return getDayWeekYear(getDate()) + " - " + getStartTime() + " to " + getEndTime()
                + " with Prof. " + professor.getName() + " (" + professor.getAlias().toUpperCase() + ")";
    }

    // For showing on prof page
    public String toStringProf(){
        return getDayWeekYear(getDate()) + " - " + getStartTime() + " to " + getEndTime()
                + " with " + studentName + " (" + studentId + ")";
    }

    private String getDayWeekYear(LocalDate date) {
        String dateString = date.toString();
        String[] dateArr = dateString.split("-");
        return dateArr[2] + "/" + dateArr[1] + "/" + dateArr[0].substring(2, 4);
    }

    @Override
    // Slots are considered equal if both prof and timestamp are the same
    // This is because prof and timestamp are declared UNIQUE together in database
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

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
