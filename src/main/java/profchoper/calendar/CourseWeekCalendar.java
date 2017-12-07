package profchoper.calendar;

import profchoper.course.Course;

import java.util.List;

public class CourseWeekCalendar {
    private Course course;
    private List<List<String>> profAliasesForHTML;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<List<String>> getProfAliasesForHTML() {
        return profAliasesForHTML;
    }

    public void setProfAliasesForHTML(List<List<String>> profAliasesForHTML) {
        this.profAliasesForHTML = profAliasesForHTML;
    }
}
