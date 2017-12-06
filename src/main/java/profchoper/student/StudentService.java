package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentDAO studentDAO;
    
    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }
    
    public Student getStudentById(int id) {
        return studentDAO.findById(id);
    }
    
    public Student getStudentByEmail(String email) {
        return studentDAO.findByEmail(email);
    }
}
