package profchoper.professor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        String profName = resultSet.getString("p_name");
        String profAlias = resultSet.getString("p_alias");
        String profEmail = resultSet.getString("p_email");
        String profOffice = resultSet.getString("p_office");
        Professor professor = new Professor(profName, profAlias, profEmail, profOffice);

        String courseId = resultSet.getString("c_id");
        String courseName = resultSet.getString("c_name");
        String courseAlias = resultSet.getString("c_alias");

        professor.setCourse(new Course(courseId, courseName, courseAlias));
        return professor;
    }
}
