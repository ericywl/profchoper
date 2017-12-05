package profchoper.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
            ResultSet studentRs = stmt.executeQuery("SELECT * FROM students ORDER BY id");
            Map<Integer, ArrayList<String>> students = new HashMap<>();
            while (studentRs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(studentRs.getString("name"));
                temp.add(studentRs.getString("course1_id"));
                temp.add(studentRs.getString("course2_id"));
                temp.add(studentRs.getString("course3_id"));
                temp.add(studentRs.getString("course4_id"));

                students.put(studentRs.getInt("id"), temp);
            }

            ResultSet profRs = stmt.executeQuery("SELECT * FROM professors ORDER BY course");
            Map<String, ArrayList<String>> professors = new HashMap<>();
            while (profRs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(profRs.getString("office"));
                temp.add(profRs.getString("course_id"));

                professors.put(profRs.getString("name"), temp);
            }

            model.put("students", students);
            model.put("professors", professors);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
