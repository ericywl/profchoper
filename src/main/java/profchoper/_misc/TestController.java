package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private WeekCalendarService weekCalendarService;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/test")
    public String test(Map<String, Object> model) {
        LocalDate date = LocalDate.of(2017, 12, 4);
        WeekCalendar wkCal = weekCalendarService.getStudentCalendarByCourse(50002, date);

        model.put("calendar", wkCal.getMatrix());
        return "test";
    }

    @GetMapping("/student")
    public String student(Map<String, Object> model) {
        LocalDate date = LocalDate.of(2017, 12, 4);
        WeekCalendar wkCal = weekCalendarService.getStudentCalendarByCourse(50002, date);

        model.put("calendar", wkCal.getMatrix());
        return "student";
    }

    @GetMapping("/prof")
    public String prof(Map<String, Object> model) {
        LocalDate date = LocalDate.of(2017, 12, 4);
        WeekCalendar wkCal = weekCalendarService.getProfCalendar("OKA", date);

        model.put("calendar", wkCal.getMatrix());
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
