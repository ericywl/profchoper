package profchoper.student;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        int studentId = resultSet.getInt("s_id");
        String studentName = resultSet.getString("s_name");
        String studentEmail = resultSet.getString("s_email");

        List<Integer> courseList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            int courseId = resultSet.getInt("course" + i + "_id");
            courseList.add(courseId);
        }

        return new Student(studentId, studentName, studentEmail, courseList);
    }
}
