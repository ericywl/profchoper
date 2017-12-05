package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profchoper.course.CourseService;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepo;
}
