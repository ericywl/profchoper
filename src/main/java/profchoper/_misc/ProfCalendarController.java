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
public class ProfCalendarController {
    private final WeekCalendarService weekCalendarService;
    private final BookingSlotService bookingSlotService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final ProfChoperAuthFacade authFacade;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @Autowired
    public ProfCalendarController(WeekCalendarService weekCalendarService, StudentService studentService,
                                     ProfessorService professorService, ProfChoperAuthFacade authFacade, BookingSlotService bookingSlotService) {

        this.weekCalendarService = weekCalendarService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.bookingSlotService = bookingSlotService;
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

        // String profEmail = authFacade.getAuthentication().getName();
        String profEmail = "jit_biswas@sutd.edu.sg";
        Professor prof = professorService.getProfessorByEmail(profEmail);

        WeekCalendar wkCal = weekCalendarService
                .getProfCalendar(prof.getAlias() , startDateOfSchoolTerm,
                        startDateOfSchoolWeek);

        model.addAttribute("calendar", wkCal);
        model.addAttribute("profName", prof.getName());


        return "prof";
    }

    @GetMapping(value = "/prof/calendar", params = {"date"})
    public String getProfCalendar(@RequestParam String date, Model model) {

        String profEmail = "jit_biswas@sutd.edu.sg";
        Professor prof = professorService.getProfessorByEmail(profEmail);

        LocalDate startDateOfSchoolWeek = LocalDate.parse(date, dtf);
        LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);


        WeekCalendar wkCal = weekCalendarService
                .getProfCalendar(prof.getAlias() , startDateOfSchoolTerm,
                        startDateOfSchoolWeek);

        model.addAttribute("calendar", wkCal);
        return "fragments/prof_cal";

    }

    @PutMapping("/prof/reject")
    public boolean rejectSlot(@RequestBody BookingSlot slot) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        return bookingSlotService.rejectBookSlot(slot, prof.getAlias());
    }

    @PutMapping("/prof/confirm")
    public boolean confirmSlot(@RequestBody BookingSlot slot) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        return bookingSlotService.confirmBookSlot(slot, prof.getAlias());
    }

    @PutMapping("/prof/delete")
    public boolean deleteSlot(@RequestBody BookingSlot slot) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        return bookingSlotService.deleteSlot(slot, prof.getAlias());
    }

//    @PutMapping("/prof/add")
//    public boolean addSlot(@RequestBody BookingSlot slot) {
//        String profEmail = authFacade.getAuthentication().getName();
//        Professor prof = professorService.getProfessorByEmail(profEmail);
//
//        return bookingSlotService.addBookSlot(slot, prof.getAlias());
//    }

}
