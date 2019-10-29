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
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;

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

import static profchoper._config.Constant.CANCELLED;
import static profchoper._config.Constant.PENDING;

@Controller
public class ProfCalendarController {
    private final WeekCalendarService weekCalendarService;
    private final BookingSlotService bookingSlotService;
    private final ProfessorService professorService;
    private final ProfChoperAuthFacade authFacade;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm");

    @Autowired
    public ProfCalendarController(WeekCalendarService weekCalendarService,
                                  ProfessorService professorService, ProfChoperAuthFacade authFacade,
                                  BookingSlotService bookingSlotService) {

        this.weekCalendarService = weekCalendarService;
        this.professorService = professorService;
        this.bookingSlotService = bookingSlotService;
        this.authFacade = authFacade;
    }

    // Get main page of prof
    @GetMapping("/prof")
    public String prof(Model model) {
        try {
            LocalDate currDate = LocalDate.now();
            if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                currDate = currDate.plus(3, ChronoUnit.DAYS);

            LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);
            LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

            String profEmail = authFacade.getAuthentication().getName();
            Professor prof = professorService.getProfessorByEmail(profEmail);

            WeekCalendar wkCal = weekCalendarService
                    .getProfCalendar(prof.getAlias(), startDateOfSchoolTerm,
                            startDateOfSchoolWeek);

            List<BookingSlot> profBookings
                    = bookingSlotService.getSlotsByProfAndSWeek(prof.getAlias(), startDateOfSchoolWeek);

            model.addAttribute("calendar", wkCal);
            model.addAttribute("prof", prof);
            model.addAttribute("bookings", filterBookingSlots(profBookings));
            return "prof";

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get prof calendar (for refreshing calendar with JS)
    @GetMapping(value = "/prof/calendar", params = {"date"})
    public String getProfCalendar(@RequestParam String date, Model model) {
        try {
            String profEmail = authFacade.getAuthentication().getName();
            Professor prof = professorService.getProfessorByEmail(profEmail);

            LocalDate startDateOfSchoolWeek = LocalDate.parse(date, dtf);
            LocalDate startDateOfSchoolTerm = LocalDate.of(2017, 9, 11);

            WeekCalendar wkCal = weekCalendarService
                    .getProfCalendar(prof.getAlias(), startDateOfSchoolTerm,
                            startDateOfSchoolWeek);

            model.addAttribute("calendar", wkCal);
            return "fragments/prof_cal";

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get prof notifications (for refreshing notifications with JS)
    @GetMapping(value = "/prof/noti")
    public String getProfNotifications(Model model) {
        try {
            LocalDate currDate = LocalDate.now();
            if (currDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || currDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                currDate = currDate.plus(3, ChronoUnit.DAYS);

            LocalDate startDateOfSchoolWeek = currDate.with(DayOfWeek.MONDAY);

            String profEmail = authFacade.getAuthentication().getName();
            Professor prof = professorService.getProfessorByEmail(profEmail);

            List<BookingSlot> profBookings
                    = bookingSlotService.getSlotsByProfAndSWeek(prof.getAlias(), startDateOfSchoolWeek);

            model.addAttribute("bookings", filterBookingSlots(profBookings));
            return "fragments/prof_noti";

        } catch (Exception ex) {
            ex.printStackTrace();

            model.addAttribute("message", ex.toString());
            return "error";
        }
    }

    // Get prof confirmed bookings (for getting student's name on JS)
    @ResponseBody
    @GetMapping(value = "/prof/bookings", params = {"datetime"})
    public Map<String, String> getProfBooking(@RequestParam String datetime) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse(datetime, DATE_TIME_FORMATTER));
        BookingSlot slot = bookingSlotService.getSlotByProfAndTimestamp(prof.getAlias(), timestamp);
        String studentString = slot.getStudentName() + " (" + slot.getStudentId() + ")";

        Map<String, String> output = new HashMap<>();
        output.put("studentString", studentString);
        return output;
    }

    // Put request to update slots with confirm or reject
    @ResponseBody
    @PutMapping(value = "/prof", params = {"action"})
    public CalendarResponse confirmSlot(@RequestBody BookingSlotJS slotJS, @RequestParam String action) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        Timestamp time = Timestamp.valueOf(LocalDateTime.parse(slotJS.getTime(), DATE_TIME_FORMATTER));
        BookingSlot slot = bookingSlotService.getSlotByProfAndTimestamp(prof.getAlias(), time);

        if (action.equalsIgnoreCase("confirm")) {
            BookingSlot returnedSlot = bookingSlotService.respondBookSlot(slot, profEmail, true);
            if (returnedSlot != null)
                return new CalendarResponse("CONFIRM_DONE", returnedSlot);
            else
                return new CalendarResponse("CONFIRM_FAIL", slot);

        } else if (action.equalsIgnoreCase("reject")) {
            BookingSlot returnedSlot = bookingSlotService.respondBookSlot(slot, profEmail, false);
            if (returnedSlot != null)
                return new CalendarResponse("REJECT_DONE", returnedSlot);
            else
                return new CalendarResponse("REJECT_FAIL", slot);
        }

        return new CalendarResponse("ERROR", null);
    }

    // Delete request to deleting available slots
    @ResponseBody
    @DeleteMapping("/prof")
    public CalendarResponse deleteSlot(@RequestBody BookingSlotJS slotJS) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        Timestamp time = Timestamp.valueOf(LocalDateTime.parse(slotJS.getTime(), DATE_TIME_FORMATTER));
        BookingSlot slot = bookingSlotService.getSlotByProfAndTimestamp(prof.getAlias(), time);
        BookingSlot returnedSlot = bookingSlotService.deleteSlot(slot, profEmail);
        if (returnedSlot != null)
            return new CalendarResponse("DELETE_DONE", returnedSlot);
        else
            return new CalendarResponse("DELETE_FAIL", slot);
    }

    // Post request to add slots to blanks
    @PostMapping("/prof")
    @ResponseBody
    public CalendarResponse addSlot(@RequestBody BookingSlotJS slotJS) {
        String profEmail = authFacade.getAuthentication().getName();
        Professor prof = professorService.getProfessorByEmail(profEmail);

        Timestamp time = Timestamp.valueOf(LocalDateTime.parse(slotJS.getTime(), DATE_TIME_FORMATTER));
        BookingSlot slot = new BookingSlot(prof, time);
        BookingSlot returnedSlot = bookingSlotService.addSlot(slot, profEmail);
        if (returnedSlot != null)
            return new CalendarResponse("ADD_DONE", returnedSlot);
        else
            return new CalendarResponse("ADD_FAIL", slot);
    }

    // Make notifications display pending and cancelled slots only
    private List<BookingSlot> filterBookingSlots(List<BookingSlot> profBookings) {
        List<BookingSlot> output = new ArrayList<>();
        for (BookingSlot booking : profBookings) {
            if (booking.getBookStatus().equals(PENDING) || booking.getBookStatus().equals(CANCELLED))
                output.add(booking);
        }

        return output;
    }
}
