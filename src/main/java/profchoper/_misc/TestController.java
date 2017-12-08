package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;
import profchoper.student.Student;
import profchoper.student.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/student")
    public String student(Map<String, Object> model) {
        LocalDate date = LocalDate.of(2017, 12, 4);
        WeekCalendar wkCal = weekCalendarService.getStudentCalendarByCourse(50002, date);

        String studentEmail = authFacade.getAuthentication().getName();
        Student student = studentService.getStudentByEmail(studentEmail);
        String firstCourseId = student.getEnrolledCourses().get(0).getId();
        List<Professor> professors = professorService.getProfessorsByCourseId(firstCourseId);

        model.put("student", student);
        model.put("professors", professors);
        model.put("calendar", wkCal.getMatrix());
        return "student";
    }

    @PutMapping("/student")
    public String updateSlotStudent() {
        return "student";
    }

    @GetMapping("/prof")
    public String prof(Map<String, Object> model) {
        LocalDate date = LocalDate.of(2017, 12, 4);
        WeekCalendar wkCal = weekCalendarService.getProfCalendar("oka", date);

        model.put("calendar", wkCal.getMatrix());
        return "prof";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
