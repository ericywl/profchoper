package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.bookingSlot.BookingSlot;
import profchoper.bookingSlot.BookingSlotService;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;
import profchoper.student.Student;
import profchoper.student.StudentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class StudentCalendarController {
    private final WeekCalendarService weekCalendarService;
    private final BookingSlotService bookingSlotService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final ProfChoperAuthFacade authFacade;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @Autowired
    public StudentCalendarController(WeekCalendarService weekCalendarService, StudentService studentService,
                                     ProfessorService professorService, ProfChoperAuthFacade authFacade,
                                     BookingSlotService bookingSlotService) {

        this.bookingSlotService = bookingSlotService;
        this.weekCalendarService = weekCalendarService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.authFacade = authFacade;
    }

    @GetMapping("/student")
    public String student(Model model) {
        LocalDate currDate = LocalDate.now();
        if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
            currDate = currDate.plus(3, ChronoUnit.DAYS);

        LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);
        LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

        // String studentEmail = authFacade.getAuthentication().getName();
        String studentEmail = "eric@mymail.sutd.edu.sg";
        Student student = studentService.getStudentByEmail(studentEmail);
        String firstCourseId = student.getEnrolledCourses().get(0).getId();

        List<Professor> professors = professorService.getProfessorsByCourseId(firstCourseId);
        WeekCalendar wkCal = weekCalendarService
                .getStudentCalendarByCourse(firstCourseId, startDateOfSchoolTerm, startDateOfSchoolWeek);

        model.addAttribute("student", student);
        model.addAttribute("professors", professors);
        model.addAttribute("calendar", wkCal);

        return "student";
    }

    @GetMapping(value = "/student/calendar", params = {"date", "course", "prof"})
    public String getStudentCalendar(@RequestParam String date, @RequestParam String course,
                                     @RequestParam String prof, Model model) {

        LocalDate startDateOfSchoolWeek = LocalDate.parse(date, dtf);
        LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

        WeekCalendar wkCal;

        if (prof.equals("null")) {
            wkCal = weekCalendarService
                    .getStudentCalendarByCourse(course, startDateOfSchoolTerm, startDateOfSchoolWeek);

        } else {
            wkCal = weekCalendarService
                    .getStudentCalendarByProf(prof, startDateOfSchoolTerm, startDateOfSchoolWeek);
        }

        model.addAttribute("calendar", wkCal);
        return "fragments/student_cal";
    }

    @PutMapping(value = "/student")
    public boolean bookSlot(@RequestBody BookingSlot slot) {

        String studentEmail = authFacade.getAuthentication().getName();
        Student student = studentService.getStudentByEmail(studentEmail);

        return bookingSlotService.bookSlot(slot, student.getId());
    }

}
