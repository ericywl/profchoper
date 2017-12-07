package profchoper.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        List<Course> output = new ArrayList<>();
        courseRepository.findAll().forEach(output :: add);

        return output;
    }

    public Course getCourseById(int id) {
        return courseRepository.findOne(id);
    }
}
