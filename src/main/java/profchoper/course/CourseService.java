package profchoper.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseDAO courseDAO;

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public Course getCourseById(String id) {
        return courseDAO.findById(id);
    }
}
