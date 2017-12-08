package profchoper.calendar;

import java.time.LocalDate;
import java.util.List;

public class WeekCalendar {
    private LocalDate startDateOfSchoolWeek;
    private List<List<String>> matrix;

    public WeekCalendar(LocalDate startDateOfSchoolWeek, List<List<String>> matrix) {
        this.startDateOfSchoolWeek = startDateOfSchoolWeek;
        this.matrix = matrix;
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
}
