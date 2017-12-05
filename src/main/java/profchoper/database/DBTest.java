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
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM students");

            ArrayList<String> studentOutput = new ArrayList<>();
            while (rs1.next()) {
                studentOutput.add(rs1.getInt("id")
                        + ": " + rs1.getString("name"));
            }

            model.put("students", studentOutput);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
