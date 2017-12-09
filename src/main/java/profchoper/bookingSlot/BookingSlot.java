package profchoper.bookingSlot;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static profchoper._misc.Constant.*;

@Entity
public class BookingSlot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "start_time")
    private Timestamp timestamp;

    @Column(name = "professor_alias")
    private String profAlias;

    @Column(name = "student_id")
    private Integer studentId = null;

    @Column(name = "book_status")
    private String bookStatus = AVAIL;


    public BookingSlot() {
        // default constructor
    }

    public BookingSlot(String profAlias, Timestamp startTimestamp) {
        this.profAlias = profAlias;
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

    public LocalTime getTime() {
        return timestamp.toLocalDateTime().toLocalTime();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;

        BookingSlot comparedSlot = (BookingSlot) obj;

        if (!comparedSlot.timestamp.equals(this.timestamp))
            return false;

        if (!comparedSlot.profAlias.equalsIgnoreCase(this.profAlias))
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

    public String getProfAlias() {
        return profAlias;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setProfAlias(String profAlias) {
        this.profAlias = profAlias;
    }
}
