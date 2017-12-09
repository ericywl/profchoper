package profchoper.calendar;

import profchoper.course.Course;

import java.time.LocalDate;

public interface WeekCalendarService {
    WeekCalendar getStudentCalendarByCourse(String courseId, LocalDate startDateOfSchoolTerm,
                                            LocalDate startDateOfSchoolWeek);

    WeekCalendar getStudentCalendarByProf(String profAlias, LocalDate startDateOfSchoolTerm,
                                          LocalDate startDateOfSchoolWeek);

    WeekCalendar getProfCalendar(String profAlias, LocalDate startDateOfSchoolTerm,
                                 LocalDate startDateOfSchoolWeek);
}
