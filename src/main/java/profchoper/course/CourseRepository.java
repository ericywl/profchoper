package profchoper.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CourseRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    public List<Course> findAll() throws SQLException {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT * FROM courses ORDER BY id";

        return select.query(selectSQL, new CourseRowMapper());
    }

    public Course findById(String id) throws SQLException {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT * FROM courses WHERE id = ?";

        return (Course) select.queryForObject(selectSQL, new Object[]{id},
                new BeanPropertyRowMapper(Course.class));
    }
}
