package profchoper.course;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CourseDAO {
    private final JdbcTemplate jdbcTemplate;

    public CourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> findAll() {
        String selectSQL = "SELECT * FROM courses ORDER BY id";

        return jdbcTemplate.query(selectSQL, new CourseRowMapper());
    }

    public Course findById(String id) {
        String selectSQL = "SELECT * FROM courses WHERE id = ?";

        return (Course) jdbcTemplate.queryForObject(selectSQL, new Object[]{id},
                new BeanPropertyRowMapper(Course.class));
    }
}
