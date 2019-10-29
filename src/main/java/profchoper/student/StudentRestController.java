package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static profchoper._config.Constant.STUDENT_EMAIL_DOMAIN;

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

    // Finding by email only requires the name, not the domain
    @GetMapping(value = "/api/students", params = "email")
    public Student getStudentByEmail(@RequestParam String email) {
        return studentService.getStudentByEmail(email + STUDENT_EMAIL_DOMAIN);
    }
}
