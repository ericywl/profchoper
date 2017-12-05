package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import profchoper.course.Course;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class StudentRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    public List<Student> findAll() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT s.id as s_id, " +
                "s.name as s_name, s.email as s_email, " +
                "c1.id as c1_id, c1.name as c1_name, c1.alias as c1_alias, " +
                "c2.id as c2_id, c2.name as c2_name, c2.alias as c2_alias," +
                "c3.id as c3_id, c3.name as c3_name, c3.alias as c3_alias," +
                "c4.id as c4_id, c4.name as c4_name, c4.alias as c4_alias " +
                "FROM students s " +
                "JOIN courses c1 ON s.course1_id = c1.id " +
                "JOIN courses c2 ON s.course1_id = c2.id " +
                "JOIN courses c3 ON s.course3_id = c3.id " +
                "JOIN courses c4 ON s.course1_id = c4.id " +
                "ORDER BY s_id";

        return select.query(selectSQL, new StudentRowMapper());
    }

    public Student findById(int id) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT * FROM students WHERE id = ?";

        return (Student) select.queryForObject(selectSQL, new Object[]{id},
                new BeanPropertyRowMapper(Student.class));
    }

}

