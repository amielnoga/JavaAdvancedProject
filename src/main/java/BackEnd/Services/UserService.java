package BackEnd.Services;

import BackEnd.DBHelper.UserDBHelper;
import BackEnd.models.Users;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * add a new user to the database
     *
     * @param user the user to add
     */
    public void addUser(Users user) {
        UserDBHelper.addUser(user);
    }

    /**
     * gets a user by name
     *
     * @param username the name of the user to get
     * @return the user object with the specified name
     */
    public Users getUserByName(String username) {
        return UserDBHelper.getUserByUsername(username);
    }

    /**
     * update the user in the database
     *
     * @param user the user to update
     */
    public void updateUser(Users user) {
        UserDBHelper.updateUser(user);
    }
}
