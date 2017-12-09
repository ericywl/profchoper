package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;
import profchoper.student.Student;
import profchoper.student.StudentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class TestController {
    private final WeekCalendarService weekCalendarService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final ProfChoperAuthFacade authFacade;

    @Autowired
    public TestController(WeekCalendarService weekCalendarService, StudentService studentService,
                          ProfessorService professorService, ProfChoperAuthFacade authFacade) {

        this.weekCalendarService = weekCalendarService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.authFacade = authFacade;
    }

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
