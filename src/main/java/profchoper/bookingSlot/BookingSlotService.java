package profchoper.bookingSlot;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingSlotService {

    List<BookingSlot> getAllSlots();


    // Slot related methods

    boolean bookSlot(BookingSlot slot, int studentID);

    boolean cancelBookSlot(BookingSlot slot, int studentID);

    boolean confirmBookSlot(BookingSlot slot, String profAlias);

    boolean deleteSlot(BookingSlot slot, String profAlias);


    // Date and Time related queries

    List<BookingSlot> getSlotsByDateTime(LocalDateTime dateTime);

    List<BookingSlot> getSlotsByDate(LocalDate date);

    List<BookingSlot> getSlotsBySchoolWeek(LocalDate startDateOfSchoolWeek);


    // Professor related queries

    List<BookingSlot> getSlotsByProfAlias(String profAlias);

    List<BookingSlot> getSlotsByCourseId(int courseId);


    // Combined

    List<BookingSlot> getSlotsByProfAndSWeek(String profAlias, LocalDate startDateOfSchoolWeek);

    List<BookingSlot> getSlotsByCourseAndSWeek(int courseId, LocalDate startDateOfSchoolWeek);
}
