package profchoper.course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    Course getCourseById(int id);
}
