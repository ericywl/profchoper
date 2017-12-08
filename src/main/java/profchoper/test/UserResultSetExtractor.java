package profchoper.test;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        int id = resultSet.getInt("id");
        String first = resultSet.getString("first");
        String last = resultSet.getString("last");
        String email = resultSet.getString("email");
        String city = resultSet.getString("city");
        String company = resultSet.getString("company");

        return new User(id, first, last, email, city, company);
    }
}
