package profchoper.calendar;


import java.time.LocalDate;

public interface WeekCalendarService {
    WeekCalendar getStudentCalendarByCourse(int studentId, String courseId, LocalDate startDateOfSchoolTerm,
                                            LocalDate startDateOfSchoolWeek);

    WeekCalendar getStudentCalendarByProf(int studentId, String profAlias, LocalDate startDateOfSchoolTerm,
                                          LocalDate startDateOfSchoolWeek);

    WeekCalendar getProfCalendar(String profAlias, LocalDate startDateOfSchoolTerm,
                                 LocalDate startDateOfSchoolWeek);
}
