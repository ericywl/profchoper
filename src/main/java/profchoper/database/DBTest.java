package profchoper.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
public class DBTest {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    @RequestMapping("/")
    String index(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            ArrayList<String> studentOutput = new ArrayList<>();
            ArrayList<ArrayList<String>> courseOutput = new ArrayList<>();
            while (rs.next()) {
                studentOutput.add(rs.getInt("id")
                        + ": " + rs.getString("name"));

                String course1 = "" + rs.getDouble("course1");
                String course2 = "" + rs.getDouble("course2");
                String course3 = "" + rs.getDouble("course3");
                String course4 = "" + rs.getDouble("course4");

                ArrayList<String> temp = new ArrayList<>(Arrays.asList(course1, course2, course3, course4));
                courseOutput.add(temp);
            }

            model.put("students", studentOutput);
            model.put("courses", courseOutput);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
