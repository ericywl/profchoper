package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static profchoper._misc.Constant.STUDENT_EMAIL_DOMAIN;

@RestController
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/api/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/api/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/api/students")
    public Student getStudentByEmail(@RequestParam("email") String emailName) {
        return studentService.getStudentByEmail(emailName + STUDENT_EMAIL_DOMAIN);
    }
}
