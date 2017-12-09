package profchoper.bookingSlot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingSlotRestController {

    @Autowired
    private BookingSlotService slotService;
}
