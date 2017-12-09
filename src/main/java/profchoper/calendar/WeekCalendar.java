package profchoper.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class WeekCalendar {
    private LocalDate startDateOfSchoolTerm;
    private LocalDate startDateOfSchoolWeek;
    private List<List<String>> matrix;

    private static final DateTimeFormatter DAY_MONTH = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public WeekCalendar(LocalDate startDateOfSchoolTerm, LocalDate startDateOfSchoolWeek,
                        List<List<String>> matrix) {
        this.startDateOfSchoolTerm = startDateOfSchoolTerm;
        this.startDateOfSchoolWeek = startDateOfSchoolWeek;
        this.matrix = matrix;
    }

    public String getHeader() {
        LocalDate endDateOfSchoolWeek = startDateOfSchoolWeek.plus(5, ChronoUnit.DAYS);

        return DAY_MONTH.format(startDateOfSchoolWeek) + " - " + DAY_MONTH.format(endDateOfSchoolWeek);
    }

    public List<List<String>> getMatrix() {
        return matrix;
    }
    
    public void setMatrix(List<List<String>> setMatrix) {
        this.matrix = setMatrix;
    }
    
    public LocalDate getStartDateOfSchoolWeek() {
        return startDateOfSchoolWeek;
    }
    
    public void setStartDateOfSchoolWeek(LocalDate startDateOfSchoolWeek) {
        this.startDateOfSchoolWeek = startDateOfSchoolWeek;
    }

    public LocalDate getStartDateOfSchoolTerm() {
        return startDateOfSchoolTerm;
    }

    public void setStartDateOfSchoolTerm(LocalDate startDateOfSchoolTerm) {
        this.startDateOfSchoolTerm = startDateOfSchoolTerm;
    }

    public static void main(String[] args) {
        LocalDate startDateOfSchoolWeek = LocalDate.of(2017, 12, 4);
        LocalDate endDateOfSchoolWeek = startDateOfSchoolWeek.plus(5, ChronoUnit.DAYS);

        System.out.println(DAY_MONTH.format(startDateOfSchoolWeek)
                + " - " + DAY_MONTH.format(endDateOfSchoolWeek));
    }
}
