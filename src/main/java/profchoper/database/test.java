package profchoper.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import profchoper.user.Student;
import static profchoper.database.DBContract.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class test {
    @Autowired
    private DataSource dataSource;

    @RequestMapping("/db")
    String db(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS students");
            stmt.executeUpdate("CREATE TABLE students (id INTEGER PRIMARY KEY, name TEXT)");

            Student eric = new Student(1002394, "Eric");
            Student wentat = new Student(1002323, "Wen Tat");
            insertStudent(connection, eric);
            insertStudent(connection, eric);
            insertStudent(connection, wentat);
            insertStudent(connection, wentat);

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            ArrayList<String> output = new ArrayList<>();
            while (rs.next()) {
                output.add("Read from DB: " + rs.getInt("id")
                        + " " + rs.getString("name"));
            }

            model.put("records", output);
            return "db";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    private void insertStudent(Connection connection, Student student) throws SQLException {
        String insertStudentSQL = "INSERT INTO students (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";

        PreparedStatement pstmt = connection.prepareStatement(insertStudentSQL);
        pstmt.setInt(1, student.getStudentId());
        pstmt.setString(2, student.getStudentName());
        pstmt.execute();

    }
}
