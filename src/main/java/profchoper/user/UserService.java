package profchoper.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    List<User> getUsersByRole(String role);
}
