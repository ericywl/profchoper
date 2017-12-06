package profchoper.calendar;

import profchoper.slot.Slot;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.AVAIL;

public class WeekCalendarSlotHandler {
    private final String handlerID;
    private List<Slot> slotList = new ArrayList<>();
    private LocalDateTime dateTime = null;

    public WeekCalendarSlotHandler(String handlerID) {
        this.handlerID = handlerID;
    }

    @Override
    public String toString() {
        StringBuilder outputBld = new StringBuilder();

        for (int i = 0; i < slotList.size(); i++) {
            Slot slot = slotList.get(i);
            outputBld.append(slot.getProfAlias());

            if (i != slotList.size() - 1)
                outputBld.append("\n");
        }

        return outputBld.toString();
    }

    public void addSlot(Slot slot){
        this.slotList.add(slot);
    }

    public boolean isEmpty() {
        return slotList.isEmpty();
    }

    public String getHandlerID() {
        return handlerID;
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Slot> getSlotList() {
        return slotList;
    }
}
