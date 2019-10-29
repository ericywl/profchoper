package profchoper._controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import profchoper._security.ProfChoperAuthFacade;
import profchoper.booking.BookingSlot;
import profchoper.booking.BookingSlotJS;
import profchoper.booking.BookingSlotService;
import profchoper.calendar.WeekCalendar;
import profchoper.calendar.WeekCalendarService;
import profchoper.course.Course;
import profchoper.course.CourseService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static profchoper._config.Constant.*;

@Controller
public class StudentCalendarController {
    private final WeekCalendarService weekCalendarService;
    private final BookingSlotService bookingSlotService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final CourseService courseService;
    private final ProfChoperAuthFacade authFacade;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm");

    @Autowired
    public StudentCalendarController(WeekCalendarService weekCalendarService, StudentService studentService,
                                     ProfessorService professorService, ProfChoperAuthFacade authFacade,
                                     BookingSlotService bookingSlotService, CourseService courseService) {

        this.bookingSlotService = bookingSlotService;
        this.weekCalendarService = weekCalendarService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.authFacade = authFacade;
        this.courseService = courseService;
    }

    // Get main page of student
    @GetMapping("/student")
    public String student(Model model) {
        try {
            LocalDate currDate = LocalDate.now();
            if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                currDate = currDate.plus(3, ChronoUnit.DAYS);

            LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);
            LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

            String studentEmail = authFacade.getAuthentication().getName();
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

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get student calendar (for refreshing calendar with JS)
    @GetMapping(value = "/student/calendar", params = {"date", "course", "prof"})
    public String getStudentCalendar(@RequestParam String date, @RequestParam String course,
                                     @RequestParam String prof, Model model) {

        try {
            String studentEmail = authFacade.getAuthentication().getName();
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

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get student notifications (for refreshing notifications with JS)
    @GetMapping(value = "/student/noti")
    public String getStudentNotifications(Model model) {
        try {
            LocalDate currDate = LocalDate.now();
            if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                currDate = currDate.plus(3, ChronoUnit.DAYS);

            LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);

            String studentEmail = authFacade.getAuthentication().getName();
            Student student = studentService.getStudentByEmail(studentEmail);

            List<BookingSlot> studentBookings
                    = bookingSlotService.getSlotsByStudentAndSWeek(student.getId(), startDateOfSchoolWeek);

            model.addAttribute("bookings", filterStudentBookings(studentBookings));
            return "fragments/student_noti";

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get student's number of bookings per week per course
    @ResponseBody
    @GetMapping(value = "/student/bookings", params = {"date", "course"})
    public Map<String, Integer> getStudentBookingsNo(@RequestParam String date,
                                                     @RequestParam String course) {

        String studentEmail = authFacade.getAuthentication().getName();
        Student student = studentService.getStudentByEmail(studentEmail);
        LocalDate startDateOfSchoolWeek = LocalDate.parse(date, DATE_FORMATTER);

        List<BookingSlot> studentBookings
                = bookingSlotService.getSlotsByStudentAndSWeek(student.getId(), startDateOfSchoolWeek);

        Course comparedCourse = courseService.getCourseById(course);

        int courseBookingsNo = 0;
        for (BookingSlot slot : studentBookings) {
            if (slot.getProfessor().getCourse().equals(comparedCourse))
                if (slot.getBookStatus().equalsIgnoreCase(BOOKED)
                        || slot.getBookStatus().equalsIgnoreCase(PENDING)) {
                    courseBookingsNo++;
                }
        }

        Map<String, Integer> output = new HashMap<>();
        output.put("courseBookingsNo", courseBookingsNo);
        return output;
    }

    // Put request to update slots with book or cancel
    @ResponseBody
    @PutMapping(value = "/student", params = {"action"})
    public CalendarResponse cancelSlot(@RequestBody BookingSlotJS slotJS, @RequestParam String
            action) {

        String studentEmail = authFacade.getAuthentication().getName();
        Timestamp time = Timestamp.valueOf(LocalDateTime.parse(slotJS.getTime(), DATE_TIME_FORMATTER));
        String profAlias = slotJS.getProfAlias();

        BookingSlot slot = bookingSlotService.getSlotByProfAndTimestamp(profAlias, time);

        if (action.equalsIgnoreCase("book")) {
            BookingSlot returnedSlot = bookingSlotService.bookSlot(slot, studentEmail);
            if (returnedSlot != null)
                return new CalendarResponse("BOOK_DONE", returnedSlot);
            else
                return new CalendarResponse("BOOK_FAIL", slot);

        } else if (action.equalsIgnoreCase("cancel")) {
            BookingSlot returnedSlot = bookingSlotService.cancelBookedSlot(slot, studentEmail);
            if (returnedSlot != null)
                return new CalendarResponse("CANCEL_DONE", returnedSlot);
            else
                return new CalendarResponse("CANCEL_FAIL", slot);
        }

        return new CalendarResponse("ERROR", null);
    }

    private List<BookingSlot> filterStudentBookings(List<BookingSlot> studentBookings) {
        List<BookingSlot> output = new ArrayList<>();
        for (BookingSlot booking : studentBookings) {
            if (booking.getBookStatus().equalsIgnoreCase(PENDING)
                    || booking.getBookStatus().equalsIgnoreCase(CANCELLED)
                    || booking.getBookStatus().equalsIgnoreCase(BOOKED)) {
                output.add(booking);
            }
        }

        return output;
    }

}
