package profchoper.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return slotDAO.findByDate(date);
    }

}
