package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.professor.ProfessorService;
import profchoper.student.StudentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class ProfCalendarController {
    private final WeekCalendarService weekCalendarService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final ProfChoperAuthFacade authFacade;

    @Autowired
    public ProfCalendarController(WeekCalendarService weekCalendarService, StudentService studentService,
                                     ProfessorService professorService, ProfChoperAuthFacade authFacade) {

        this.weekCalendarService = weekCalendarService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.authFacade = authFacade;
    }

    @GetMapping("/prof")
    public String prof(Model model) {
        LocalDate currDate = LocalDate.now();
        if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
            currDate = currDate.plus(3, ChronoUnit.DAYS);

        LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);
        LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

        WeekCalendar wkCal = weekCalendarService
                .getProfCalendar("oka", startDateOfSchoolTerm, startDateOfSchoolWeek);

        model.addAttribute("calendar", wkCal.getMatrix());
        return "prof";
    }

    @GetMapping("/prof/calendar")
    public String getProfCalendar(Model model) {
        return "prof";
    }
}
