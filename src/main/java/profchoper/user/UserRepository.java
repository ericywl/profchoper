package profchoper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String selectSQL = "SELECT * FROM user_roles";

        return jdbcTemplate.query(selectSQL, new UserRowMapper());
    }

    // Username is email
    public User findByUsername(String username) {
        String selectSQL = "SELECT * FROM user_roles WHERE username = ?";

        return (User) jdbcTemplate.queryForObject(selectSQL, new Object[]{username}, new UserRowMapper());
    }

    public List<User> findByRole(String role) {
        String selectSQL = "SELECT * FROM user_roles WHERE role = ?";

        return jdbcTemplate.query(selectSQL, new UserRowMapper());
    }
}
