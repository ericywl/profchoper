package profchoper.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepo;

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Course getCourseById(String id) {
        return courseRepo.findById(id);
    }
}
