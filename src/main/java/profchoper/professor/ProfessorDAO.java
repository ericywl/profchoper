package profchoper.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class ProfessorDAO {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    public List<Professor> findAll() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "ORDER BY p.course_id";

        return select.query(selectSQL, new ProfessorRowMapper());
    }

    public List<Professor> findByCourseId(String courseId) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE p.course_id = ?";

        return select.query(selectSQL, new Object[]{courseId}, new ProfessorRowMapper());
    }

    public Professor findByAlias(String alias) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE p.alias = ?";

        return (Professor) select.queryForObject(selectSQL, new Object[]{alias},
                new BeanPropertyRowMapper(Professor.class));
    }
}
