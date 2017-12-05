package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service("studentService")
public class StudentService {
    @Autowired
    @Qualifier("studentRepo")
    private StudentRepository studentRepo;

    public List<Student> getAllStudents() throws SQLException {
        return studentRepo.findAll();
    }
}
