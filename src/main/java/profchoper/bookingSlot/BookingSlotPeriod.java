package profchoper.bookingSlot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.SLOT_TIME;

// For inserting into database
public class BookingSlotPeriod {
    private List<BookingSlot> bookingSlots = new ArrayList<>();

    public BookingSlotPeriod(String profAlias, LocalDateTime startDateTime, long durationInMinutes)
            throws BookingSlotException {
        for (int i = 0; i < durationInMinutes; i += SLOT_TIME) {
            LocalDateTime slotStartTime = startDateTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
            BookingSlot newSlot = new BookingSlot(profAlias, slotStartTime);

            bookingSlots.add(newSlot);
        }
    }

    public List<BookingSlot> getBookingSlots() {
        return bookingSlots;
    }
}


