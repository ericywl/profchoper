package profchoper.calendar;

import profchoper.slot.Slot;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static profchoper._misc.Constant.AVAIL;

public class CalendarSlotHandler {
    private final String handlerID;
    private List<Slot> slotList;
    private LocalDateTime dateTime;

    public CalendarSlotHandler(String handlerID) {
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

    public void setSlotList(List<Slot> slotList) {
        this.slotList = slotList;
    }

    public void addSlot(Slot slot){
        this.slotList.add(slot);
    }

    public Boolean getAvailability() {
        for (Slot slot: slotList) {
            if (slot.getBookStatus().equals(AVAIL)) {
                return true;
            }
        }

        return false;
    }
}
