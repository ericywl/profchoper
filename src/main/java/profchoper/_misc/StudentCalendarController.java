package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.booking.BookingSlot;
import profchoper.booking.BookingSlotJS;
import profchoper.booking.BookingSlotService;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;
import profchoper.student.Student;
import profchoper.student.StudentService;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm");

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
                .getStudentCalendarByCourse(student.getId(), firstCourseId, startDateOfSchoolTerm,
                        startDateOfSchoolWeek);

        List<BookingSlot> studentBookings
                = bookingSlotService.getSlotsByStudentAndSWeek(student.getId(), startDateOfSchoolWeek);

        model.addAttribute("student", student);
        model.addAttribute("professors", professors);
        model.addAttribute("calendar", wkCal);
        model.addAttribute("bookings", studentBookings);

        return "student";
    }

    @GetMapping(value = "/student/calendar", params = {"date", "course", "prof"})
    public String getStudentCalendar(@RequestParam String date, @RequestParam String course,
                                     @RequestParam String prof, Model model) {

        String studentEmail = "eric@mymail.sutd.edu.sg";
        // String studentEmail = authFacade.getAuthentication().getName();
        Student student = studentService.getStudentByEmail(studentEmail);

        LocalDate startDateOfSchoolWeek = LocalDate.parse(date, DATE_FORMATTER);
        LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

        WeekCalendar wkCal;

        if (prof.equals("null")) {
            wkCal = weekCalendarService
                    .getStudentCalendarByCourse(student.getId(), course, startDateOfSchoolTerm,
                            startDateOfSchoolWeek);

        } else {
            wkCal = weekCalendarService
                    .getStudentCalendarByProf(student.getId(), prof, startDateOfSchoolTerm,
                            startDateOfSchoolWeek);
        }

        model.addAttribute("calendar", wkCal);
        return "fragments/student_cal";
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/student", params = {"action"})
    public StudentCalendarResponse cancelSlot(@RequestBody BookingSlotJS slotJS, @RequestParam String
            action) {
        // String studentEmail = authFacade.getAuthentication().getName();
        String studentEmail = "eric@mymail.sutd.edu.sg";
        Student student = studentService.getStudentByEmail(studentEmail);

        Timestamp time = Timestamp.valueOf(LocalDateTime.parse(slotJS.getTime(), DATE_TIME_FORMATTER));
        BookingSlot slot = new BookingSlot(slotJS.getProfAlias(), time);

        if (action.equalsIgnoreCase("book")) {
            boolean isDone = bookingSlotService.bookSlot(slot, student.getId());
            if (isDone)
                return new StudentCalendarResponse("BOOK_DONE", slot);
            else
                return new StudentCalendarResponse("BOOK_FAIL", slot);

        } else if (action.equalsIgnoreCase("cancel")) {
            boolean isDone = bookingSlotService.cancelBookSlot(slot, student.getId());
            if (isDone)
                return new StudentCalendarResponse("CANCEL_DONE", slot);
            else
                return new StudentCalendarResponse("CANCEL_FAIL", slot);
        }

        return new StudentCalendarResponse("ERROR", null);
    }

}
