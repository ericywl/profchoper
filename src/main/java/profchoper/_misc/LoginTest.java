package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import profchoper.slot.Slot;
import profchoper.slot.SlotService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class LoginTest {

    @Autowired
    private SlotService slotService;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/student")
    public String student(Map<String, Object> model) {
        try {
            LocalDate date = LocalDate.of(2017, 12, 4);
            List<Slot> slotList = slotService.getSlotsBySchoolWeek(date);
            List<List<String>> test = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                test.add(Arrays.asList("A\nAA", "B", "C", "D", "E"));
            }

            model.put("bookings", slotList);
            model.put("test", test);
            return "student";
        } catch (Exception ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/prof")
    public String prof() {
        return "prof";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
