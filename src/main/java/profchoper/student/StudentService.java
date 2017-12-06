package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentDAO studentRepo;
    
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    
    public Student getStudentById(int id) {
        return studentRepo.findById(id);
    }
    
    public Student getStudentByEmail(String email) {
        return studentRepo.findByEmail(email);
    }
}
