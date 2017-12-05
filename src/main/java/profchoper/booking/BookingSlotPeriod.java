package profchoper.booking;

import profchoper.professor.Professor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.*;


public class BookingSlotPeriod {
    private List<BookingSlot> bookingSlots = new ArrayList<>();

    public BookingSlotPeriod(Professor professor, LocalDateTime startDateTime, long durationInMinutes)
            throws BookingSlotException {
        for (int i = 0; i < durationInMinutes; i += SLOT_TIME) {
            LocalDateTime slotStartTime = startDateTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
            BookingSlot newSlot = new BookingSlot(professor, slotStartTime);

            bookingSlots.add(newSlot);
        }
    }

    public List<BookingSlot> getBookingSlots() {
        return bookingSlots;
    }
}


