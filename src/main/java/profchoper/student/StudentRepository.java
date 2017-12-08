package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> findAll() {
        String selectSQL = "SELECT * FROM students ORDER BY id";

        return jdbcTemplate.query(selectSQL, new StudentRowMapper());
    }

    public Student findById(int id) {
        String selectSQL = "SELECT * FROM students WHERE id = ?";

        return (Student) jdbcTemplate.queryForObject(selectSQL, new Object[]{id},
                new BeanPropertyRowMapper(Student.class));
    }

    public Student findByEmail(String email) {
        String selectSQL = "SELECT * FROM students WHERE email = ?";

        return (Student) jdbcTemplate.queryForObject(selectSQL, new Object[]{email},
                new BeanPropertyRowMapper(Student.class));
    }
}
