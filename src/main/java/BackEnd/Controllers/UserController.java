package BackEnd.Controllers;

import BackEnd.Services.UserService;
import BackEnd.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService; // the service class that handles the Database for users

    /**
     * add a new user to the database
     *
     * @param user the user to add
     */
    @PostMapping("/add")
    public void addUser(@RequestBody Users user) {
        userService.addUser(user);
    }

    /**
     * gets a user by name
     *
     * @param username the name of the user to get
     * @return the user object with the specified name
     */
    @GetMapping("/{username}")
    public Users getUserByName(@PathVariable String username) {
        return userService.getUserByName(username);
    }

    /**
     * update the user in the database
     *
     * @param user the user to update
     */
    @PutMapping("/update")
    public void updateUser(@RequestBody Users user) {
        userService.updateUser(user);
    }

}
