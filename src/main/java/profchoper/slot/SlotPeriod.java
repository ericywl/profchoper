package profchoper.slot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.SLOT_TIME;

// For inserting into database
public class SlotPeriod {
    private List<Slot> bookingSlots = new ArrayList<>();

    public SlotPeriod(String profAlias, LocalDateTime startDateTime, long durationInMinutes)
            throws SlotException {
        for (int i = 0; i < durationInMinutes; i += SLOT_TIME) {
            LocalDateTime slotStartTime = startDateTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
            Slot newSlot = new Slot(profAlias, slotStartTime);

            bookingSlots.add(newSlot);
        }
    }

    public List<Slot> getBookingSlots() {
        return bookingSlots;
    }
}


