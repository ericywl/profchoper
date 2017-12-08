package profchoper.test;


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

    private DataSource dataSource;

    @RequestMapping("/c")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/c/createuserform")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createuser";
    }

    @RequestMapping("/c/users")
    public String users(Model model) {
        try {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            String sql;
            sql = "SELECT id, first, last, email, company, city FROM cuser";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuffer sb = new StringBuffer();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String first = rs.getString("first");
                String last = rs.getString("last");
                String email = rs.getString("email");
                String company = rs.getString("company");
                String city = rs.getString("city");
                users.add(new User(id, first, last, email, company, city));
            }
            model.addAttribute("users", users);
            return "user";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @RequestMapping(value = "/c/createuser", method = RequestMethod.POST)
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
