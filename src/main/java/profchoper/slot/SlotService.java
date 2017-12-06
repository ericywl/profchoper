package profchoper.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    private SlotDAO slotDAO;

    public List<Slot> getAllSlots() {
        return slotDAO.findAll();
    }

    public List<Slot> getSlotsByProfAlias(String profAlias) {
        return slotDAO.findByProfAlias(profAlias);
    }

    public List<Slot> getSlotsByDate(LocalDate date) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.plus(1, ChronoUnit.DAYS).atStartOfDay();

        return slotDAO.findByDateRange(startDateTime, endDateTime);
    }

    public List<Slot> getSlotsByWeek(LocalDate startDateOfWeek) {
        LocalDateTime startDateTime = startDateOfWeek.atStartOfDay();
        LocalDateTime endDateTime = startDateOfWeek.plus(1, ChronoUnit.WEEKS).atStartOfDay();

        return slotDAO.findByDateRange(startDateTime, endDateTime);
    }
}
