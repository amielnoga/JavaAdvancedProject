package BackEnd.beans;

import BackEnd.models.Cities;
import BackEnd.models.Users;
import BackEnd.utils.PasswordUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static BackEnd.utils.PasswordUtil.hashPassword;

/**
 * This class represents a user browsing in Adopet Application.
 */
@Component("UserBean")
@SessionScoped
public class UserBean implements Serializable {
    private final static String URL = "http://localhost:8080";

    private int userId;
    private String userName;
    private String password;
    private boolean isAdmin;
    private String firstName;
    private String lastName;
    private String email;
    private int cityId;
    private String phoneNum;
    private boolean loggedIn = false;
    private String passwordVerification;
    private Users loggedInUser;

    /**
     * Default constructor for an anonymous user.
     */
    public UserBean() {
    }

    /**
     * Constructs a new UserBean with the provided user details.
     */
    public UserBean(String userName, String password, String firstName, String lastName, String phoneNum, int cityId,
                    boolean adminPerms, String email, int userId) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.cityId = cityId;
        this.isAdmin = adminPerms;
        this.email = email;
        this.userId = userId;
    }

    /**
     * @return The user ID of the user (not visible to the user).
     */
    public int getId() {
        return userId;
    }

    /**
     * @param id The user ID that the server create(not visible to the user).
     */
    public void setId(int id) {
        this.userId = id;
    }

    /**
     * @return The username of the user that the user chose.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The username the user fills in the registration form.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param password The password the user fills in the registration form / change password form
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The password the user fills in the registration form.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return The user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first name the user fills in the registration form / my profile form
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return true if the user is admin or false otherwise.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin true if the user is admin or false otherwise.
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return The user last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last name the user fills in the registration form / my profile form
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The email the user fills in the registration form / my profile form
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email the user fills in the registration form / my profile form
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The city the user fills in the registration form / my profile form
     */
    public int getCity() {
        return cityId;
    }

    /**
     * @param cityId The city the user fills in the registration form / my profile form
     */
    public void setCity(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        Cities city = CitiesBean.getCityById(this.cityId);
        return city.getName();
    }

    /**
     * @return The user phone number he fills in the registration form / my profile form
     */
    public String getPhoneNumber() {
        return phoneNum;
    }

    /**
     * @param phoneNumber The phone number the user fills in the registration form / my profile form
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNum = phoneNumber;
    }

    /**
     * @return The password verification the user fills in the registration form  / change password form
     */
    public String getPasswordVerification() {
        return passwordVerification;
    }

    /**
     * @param passwordVerification The password verification the user fills in the registration form / change
     *                             password form
     */
    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    /**
     * After the user logs in, the UserBeen is initialized with the parameters entering during login (username and
     * password). This method queries the DB for all the user information and initialized the bean.
     *
     * @return The page to redirect to, in case of failure null to stay in the Login Page
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Users> response = restTemplate.getForEntity(URL + "/users/{%s}", Users.class, userName);
            Users user;
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                user = response.getBody();
                String hashedPassword = user.getUserPassword();
                if (hashedPassword != null && PasswordUtil.checkPassword(password, hashedPassword)) {
                    this.loggedInUser = user;
                    this.firstName = user.getUserFirstName();
                    this.lastName = user.getUserLastName();
                    this.email = user.getUserEmail();
                    this.phoneNum = user.getUserPhoneNum();
                    this.cityId = user.getUserCityId();
                    this.isAdmin = user.getUserAdminPerms();
                    this.userId = user.getUserId();
                    this.loggedIn = true;
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", loggedInUser);
                    context.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "You're now logged in.", null));
                    return "home?faces-redirect=true";
                }
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed invalid password or username", null));
            return null;
        }
        catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * Logs out the current user by clearing all user-related data to their default values
     *
     * @return a navigation string that redirects the user to the login page
     */
    public String logout() {
        this.loggedInUser = null;
        this.userName = null;
        this.password = null;
        this.loggedIn = false;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phoneNum = null;
        this.cityId = 0;
        this.isAdmin = false;
        this.userId = 0;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "You're now logged out", null));
        return "login?faces-redirect=true";
    }

    /**
     * @return true if the user is currently logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * creates a map of user data to be sent to the server
     *
     * @param userName   username
     * @param password   password
     * @param firstName  first name
     * @param lastName   last name
     * @param phoneNum   phone number
     * @param cityId     city id
     * @param adminPerms admin permissions
     * @param email      email
     * @param userId     user id
     * @return hashmap of user data to use with rest
     */
    private Map<String, Object> createRestMap(String userName, String password, String firstName, String lastName,
                                              String phoneNum, int cityId, boolean adminPerms, String email,
                                              int userId) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        if (password.equals("null")) {
            userData.put("password", password);
        } else {
            userData.put("password", hashPassword(password));
        }
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        userData.put("phoneNum", phoneNum);
        userData.put("cityId", cityId);
        userData.put("adminPerms", adminPerms);
        userData.put("email", email);
        userData.put("userId", userId);
        return userData;
    }

    /**
     * Handles the user sign-up process. This method validates the user input,
     * including password confirmation and username uniqueness.
     * If the passwords do not match, or if the username is already taken,
     * appropriate error messages will be displayed and the user remains on the sign-up page.
     * If validation passes, the method registers the new user in the database (including hashing password), and
     * redirects to the login page.
     *
     * @return a navigation string to redirect to the login page on success, or null to stay on the current page if
     * validation fails.
     */
    public String signUp() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            if (!isPasswordsMatch())
                return null;
            if (!isUsernameAvailable())
                return null;
            Map<String, Object> userData = createRestMap(userName, password, firstName, lastName, phoneNum, cityId,
                    false, email, -1);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userData, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(URL + "/users/add", request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration completed, you can now log in",
                                null));
                return "login?faces-redirect=true";
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "an unexpected error occurred during registration", null));
                return null;
            }
        }catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * Validates that the password and password confirmation fields match.
     * If they do not match, an appropriate error message will be displayed to the user and the method returns false.
     * This method is declared public to allow JSF AJAX listeners to invoke it.
     *
     * @return true if the passwords match, false otherwise.
     */
    public boolean isPasswordsMatch() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pageName = context.getViewRoot().getViewId();

        String formId = null;
        if (pageName.equals("/signup.xhtml"))
            formId = "signUpForm";
        else if (pageName.contains("/changePassword.xhtml"))
            formId = "changePasswordForm";

        if (!password.equals(passwordVerification)) {
            context.addMessage(formId + ":passwordVerification",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Passwords do not match", null));
            return false;
        }
        return true;
    }

    /**
     * Checks if the entered username is available for registration.
     * This method is declared public to allow JSF AJAX listeners to invoke it.
     *
     * @return true if the username is available, false if the username is already taken.
     */
    public boolean isUsernameAvailable() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Users> response = restTemplate.getForEntity(URL + "/users/{%s}", Users.class, userName);
            Users user = response.getBody();
            if (user != null) {
                context.addMessage("signUpForm:userName",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already exists", null));
                return false;
            }
            return true;
        } catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return false;
        }
    }

    /**
     * Updates the current user's profile information.
     * If fails, an error message is displayed and the user remains on the profile page.
     * If passes, the user's data is updated in the database and the user is redirected to the home page.
     *
     * @return a navigation string to redirect to the home page on success, or null to stay on the current page if
     * validation fails.
     */
    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Map<String, Object> userData = createRestMap(userName, "null", firstName, lastName, phoneNum, cityId,
                    false, email, userId);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userData, headers);
            ResponseEntity<String> response = restTemplate.exchange(URL + "/users/update", HttpMethod.PUT, request,
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "The profile was updated successfully",
                                null));
                return "home?faces-redirect=true";
            } else {
                context.addMessage("profileForm",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "an unexpected error occurred with the " +
                                "update please try again later", null));
                return null;
            }
        }catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * Updates the current user password.
     * Validates that the password and password verification match before applying changes.
     * If the validation fails, an error message is displayed and the user remains on the change password page.
     * If the validation passes, the user's data is updated in the database and the user is redirected to the home page.
     *
     * @return a navigation string to redirect to the home page on success, or null to stay on the current page if
     * validation fails.
     */
    public String updatePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            if (!isPasswordsMatch()) {
                return null;
            }
            Map<String, Object> userData = createRestMap(userName, password, firstName, lastName, phoneNum, cityId, false
                    , email, userId);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userData, headers);
            ResponseEntity<String> response = restTemplate.exchange(URL + "/users/update", HttpMethod.PUT, request,
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "The password was updated successfully", null));
                return "home?faces-redirect=true";
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "an unexpected error occurred with the update " +
                                "please try again later", null));
                return null;
            }
        }catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * Some pages on the platform such as My Profile, Add a new Pet, My Pets, Change password are accessible only
     * to registered users. If a user is not logged in and tries to navigate to one of these pages, we notify him the
     * requested page is restricted and the method returns the string for navigation to the login page.
     *
     * @return The login page redirect string if the user is not logged in, otherwise null (allowing the current page
     * to load).
     */
    public String checkLoginRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!loggedIn) {
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage("signedOnly",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Page for " +
                            "signed in users only, please log in", null));
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }

    /**
     * Some pages on the platform such as Contact Us Requests are accessible only to managers. If a user is not logged
     * in with manager permissions and tries to navigate to one of these pages, we notify him the
     * requested page is restricted and the method returns the string for navigation to the login page.
     *
     * @return The login page redirect string if the user is not logged in with manager permissions, otherwise null
     * (allowing the current page to load).
     */
    public String checkAdminLoginRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!isAdmin) {
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage("signedOnly",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Page for " +
                            "admins only, please log in to admin user", null));
            return "login.xhtml?faces-redirect=true";
        }
        return null;
    }
}