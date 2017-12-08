package profchoper.bookingSlot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.*;

@Service
public class BookingSlotServiceImpl implements BookingSlotService {
    @Autowired
    private BookingSlotRepository slotDAO;

    @Autowired
    private ProfessorService professorService;

    @Override
    public List<BookingSlot> getAllSlots() {
        return slotDAO.findAll();
    }

    @Override
    public boolean bookSlot(BookingSlot slot, int studentID) {
        if (!slot.getBookStatus().equals(AVAIL)) return false;

        slot.setBookStatus(PENDING);
        slot.setStudentId(studentID);

        return slotDAO.update(slot);
    }

    @Override
    public boolean cancelBookSlot(BookingSlot slot, int studentID) {
        if (slot.getBookStatus().equals(AVAIL)
                || slot.getStudentId() != studentID) return false;

        slot.setBookStatus(AVAIL);
        slot.setStudentId(null);

        return slotDAO.update(slot);
    }

    @Override
    public boolean confirmBookSlot(BookingSlot slot, String profAlias) {
        if (slot.getBookStatus().equals(AVAIL)
                || !slot.getProfAlias().equals(profAlias.toLowerCase())) return false;

        slot.setBookStatus(BOOKED);

        return slotDAO.update(slot);
    }

    @Override
    public boolean deleteSlot(BookingSlot slot, String profAlias) {
        if (!slot.getBookStatus().equals(AVAIL)
                || !slot.getProfAlias().equals(profAlias.toLowerCase())) return false;

        return slotDAO.delete(slot);
    }

    @Override
    public List<BookingSlot> getSlotsByProfAlias(String profAlias) {
        return slotDAO.findByProfAlias(profAlias.toLowerCase());
    }

    @Override
    public List<BookingSlot> getSlotsByCourseId(String courseId) {
        List<Professor> professors = professorService.getProfessorsByCourseId(courseId);
        List<BookingSlot> output = new ArrayList<>();

        for (Professor prof : professors) {
            output.addAll(getSlotsByProfAlias(prof.getAlias().toLowerCase()));
        }

        return output;
    }

    @Override
    public List<BookingSlot> getSlotsByProfAndSWeek(String profAlias, LocalDate startDateOfSchoolWeek) {
        List<BookingSlot> slotList = getSlotsBySchoolWeek(startDateOfSchoolWeek);
        List<BookingSlot> output = new ArrayList<>();

        for (BookingSlot slot : slotList) {
            if (slot.getProfAlias().equalsIgnoreCase(profAlias))
                output.add(slot);
        }

        return output;
    }

    @Override
    public List<BookingSlot> getSlotsByCourseAndSWeek(String courseId, LocalDate startDateOfSchoolWeek) {
        List<BookingSlot> slotList = getSlotsBySchoolWeek(startDateOfSchoolWeek);
        List<BookingSlot> output = new ArrayList<>();
        List<Professor> professors = professorService.getProfessorsByCourseId(courseId);

        for (BookingSlot slot : slotList) {
            for (Professor prof : professors) {
                if (slot.getProfAlias().equalsIgnoreCase(prof.getAlias()))
                    output.add(slot);
            }
        }

        return output;
    }

    @Override
    public List<BookingSlot> getSlotsByDateTime(LocalDateTime dateTime) {
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        return slotDAO.findByDateTime(timestamp);
    }

    @Override
    public List<BookingSlot> getSlotsByDate(LocalDate date) {
        return getSlotsByDateRangeType(DATE, date);
    }

    @Override
    public List<BookingSlot> getSlotsBySchoolWeek(LocalDate startDateOfSchoolWeek) {
        return getSlotsByDateRangeType(SCHOOL_WEEK, startDateOfSchoolWeek);
    }

    private List<BookingSlot> getSlotsByDateRangeType(String type, LocalDate startDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime;

        switch (type) {
            case DATE:
                endDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
                break;

            case SCHOOL_WEEK:
                endDateTime = startDateTime.plus(5, ChronoUnit.DAYS);
                break;

            case WEEK:
                endDateTime = startDateTime.plus(1, ChronoUnit.WEEKS);
                break;

            default:
                endDateTime = startDateTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
                break;
        }

        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
        return slotDAO.findByDateTimeRange(startTimestamp, endTimestamp);
    }
}
