package profchoper.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/createuserform")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createuser";
    }

    @RequestMapping("/users")
    @SuppressWarnings("unchecked")
    public String users(Model model) {
        try {
            String sql = "SELECT id, first, last, email, company, city FROM cuser";
            List<User> users = jdbcTemplate.query(sql, new UserRowMapper());

            model.addAttribute("users", users);
            return "user";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        int id = user.getId();
        String first = user.getFirst();
        String last = user.getLast();
        String email = user.getEmail();
        String city = user.getCity();
        String company = user.getCompany();

        try {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "insert into cuser(first, last, email, company, city) values " +
                    "('" + first + "', '" + last + " ',' " + email + "', ' " +
                    company + "', '" + city + "');";
            ResultSet rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "result";
    }


}
