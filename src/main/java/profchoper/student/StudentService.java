package profchoper.student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentById(int id);

    Student getStudentByEmail(String email);
}
