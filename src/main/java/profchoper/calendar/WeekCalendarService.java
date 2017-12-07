package profchoper.calendar;

import profchoper.course.Course;

import java.time.LocalDate;

public interface WeekCalendarService {
    WeekCalendar getStudentCalendarByCourse(int courseId, LocalDate startDateOfSchoolWeek);

    WeekCalendar getStudentCalendarByProf(String profAlias, LocalDate startDateOfSchoolWeek);

    WeekCalendar getProfCalendar(String profAlias, LocalDate startDateOfSchoolWeek);
}
