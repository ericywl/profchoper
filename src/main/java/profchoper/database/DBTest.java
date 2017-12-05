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
            ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY id");

            Map<Integer, ArrayList<String>> students = new HashMap<>();
            while (rs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(rs.getString("name"));
                temp.add(rs.getString("course1"));
                temp.add(rs.getString("course2"));
                temp.add(rs.getString("course3"));

                students.put(rs.getInt("id"), temp);
            }

            model.put("students", students);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
