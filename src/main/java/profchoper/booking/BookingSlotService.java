package profchoper.booking;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingSlotService {

    List<BookingSlot> getAllSlots();


    // Slot related methods

    BookingSlot bookSlot(BookingSlot slot, String studentEmail);

    BookingSlot cancelBookedSlot(BookingSlot slot, String studentEmail);

    BookingSlot respondBookSlot(BookingSlot slot, String profEmail, boolean accept);

    BookingSlot addSlot(BookingSlot slot, String profEmail);

    BookingSlot deleteSlot(BookingSlot slot, String profEmail);


    // Date and Time related queries

    List<BookingSlot> getSlotsByDateTime(LocalDateTime dateTime);

    List<BookingSlot> getSlotsByDate(LocalDate date);

    List<BookingSlot> getSlotsBySchoolWeek(LocalDate startDateOfSchoolWeek);


    // Professor related queries

    List<BookingSlot> getSlotsByProfAlias(String profAlias);

    List<BookingSlot> getSlotsByCourseId(String courseId);


    // Student related queries

    List<BookingSlot> getSlotsByStudentId(int studentId);


    // Combined

    List<BookingSlot> getSlotsByProfAndSWeek(String profAlias, LocalDate startDateOfSchoolWeek);

    List<BookingSlot> getSlotsByCourseAndSWeek(String courseId, LocalDate startDateOfSchoolWeek);

    List<BookingSlot> getSlotsByStudentAndSWeek(int studentId, LocalDate startDateOfSchoolWeek);

    BookingSlot getSlotByProfAndTimestamp(String profAlias, Timestamp startTime);
}
