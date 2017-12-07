package profchoper.calendar;

import java.time.LocalDate;
import java.util.List;

public class WeekCalendar {
    private LocalDate startDateOfSchoolWeek;
    private List<List<String>> matrixForHTML;
    
    public List<List<String>> getMatrixForHTML() {
        return matrixForHTML;
    }
    
    public void setMatrixForHTML(List<List<String>> matrixForHTML) {
        this.matrixForHTML = matrixForHTML;
    }
    
    public LocalDate getStartDateOfSchoolWeek() {
        return startDateOfSchoolWeek;
    }
    
    public void setStartDateOfSchoolWeek(LocalDate startDateOfSchoolWeek) {
        this.startDateOfSchoolWeek = startDateOfSchoolWeek;
    }
}
