package profchoper.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class DBTest {
    @Autowired
    private DataSource profChoperDataSource;

    @RequestMapping("/")
    String index(Map<String, Object> model) {
        try (Connection connection = profChoperDataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            ArrayList<String> output = new ArrayList<>();
            while (rs.next()) {
                output.add(+ rs.getInt("id")
                        + ": " + rs.getString("name"));
            }

            model.put("students", output);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
