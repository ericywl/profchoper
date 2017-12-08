package profchoper.professor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        String profName = resultSet.getString("name");
        String profAlias = resultSet.getString("alias");
        String profEmail = resultSet.getString("email");
        String profOffice = resultSet.getString("office");
        int courseId = resultSet.getInt("course_id");

        return new Professor(profName, profAlias, profEmail, profOffice, courseId);
    }
}
