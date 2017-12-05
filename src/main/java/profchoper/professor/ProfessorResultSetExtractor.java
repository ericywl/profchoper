package profchoper.professor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Professor professor = new Professor();
        professor.setName(resultSet.getString("p_name"));
        professor.setAlias(resultSet.getString("p_alias"));
        professor.setEmail(resultSet.getString("p_email"));
        professor.setOfficeLocation(resultSet.getString("p_office"));

        Course course = new Course();
        course.setId(resultSet.getString("c_id"));
        course.setName(resultSet.getString("c_name"));
        course.setAlias(resultSet.getString("c_alias"));

        professor.setCourse(course);
        return professor;
    }
}
