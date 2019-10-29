package profchoper.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// Extracts results from SQL queries and return user
public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        String username = resultSet.getString("username");
        String role = resultSet.getString("role");
        
        return new User(username, role);
    }
}
