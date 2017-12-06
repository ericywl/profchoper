package profchoper.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static profchoper._misc.Constant.*;

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

    private List<Slot> getSlotsByDateRangeType(String type, LocalDate startDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime;

        switch (type) {
            case "DATE":
                endDateTime = startDate.plus(1, ChronoUnit.DAYS).atStartOfDay();
                break;

            case "WEEK":
                endDateTime = startDate.plus(1, ChronoUnit.WEEKS).atStartOfDay();
                break;

            case "MONTH":
                endDateTime = startDate.plus(1, ChronoUnit.MONTHS).atStartOfDay();
                break;

            case "TERM":
                endDateTime = startDate.plus(14, ChronoUnit.MONTHS).atStartOfDay();
                break;

            default:
                endDateTime = startDateTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
        }

        return slotDAO.findByDateRange(startDateTime, endDateTime);
    }

    public List<Slot> getSlotsByDate(LocalDate date) {
        return getSlotsByDateRangeType(DATE, date);
    }

    public List<Slot> getSlotsByWeek(LocalDate startDateOfWeek) {
        return getSlotsByDateRangeType(WEEK, startDateOfWeek);
    }

    public List<Slot> getSlotsByMonth(LocalDate startDateOfMonth) {
        return getSlotsByDateRangeType(MONTH, startDateOfMonth);

    }

    public List<Slot> getSlotsByTerm(LocalDate startDateOfTerm) {
        return getSlotsByDateRangeType(TERM, startDateOfTerm);
    }
}
