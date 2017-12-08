package profchoper.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class ProfessorRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfessorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Professor> findAll() {
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "ORDER BY p.alias";

        return jdbcTemplate.query(selectSQL, new ProfessorRowMapper());
    }

    public List<Professor> findByCourseId(int courseId) {
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE p.course_id = ? " +
                "ORDER BY p.alias";

        return jdbcTemplate.query(selectSQL, new Object[]{courseId}, new ProfessorRowMapper());
    }

    public Professor findByAlias(String alias) {
        String selectSQL = "SELECT p.name AS p_name, p.email AS p_email, " +
                "p.alias AS p_alias, p.office AS p_office, " +
                "c.name AS c_name, c.id AS c_id, c.alias AS c_alias " +
                "FROM professors p " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE p.alias = ?";

        return (Professor) jdbcTemplate.queryForObject(selectSQL, new Object[]{alias},
                new ProfessorRowMapper());
    }
}