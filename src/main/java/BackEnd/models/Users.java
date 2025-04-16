package BackEnd.models;

import BackEnd.DBHelper.CitiesDBHelper;
import BackEnd.beans.UserBean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
    private int userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private int cityId;
    private boolean adminPerms;
    private String email;

    /**
     * Constructor for the Users class.
     *
     * @param userName   the username of the user
     * @param password   the password of the user
     * @param firstName  the first name of the user
     * @param lastName   the last name of the user
     * @param phoneNum   the phone number of the user
     * @param cityId     the ID of the city the user lives in
     * @param adminPerms the admin permissions of the user
     * @param email      the email of the user
     * @param userId     the ID of the user
     */
    @JsonCreator
    public Users(@JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("phoneNum") String phoneNum, @JsonProperty("cityId") int cityId, @JsonProperty("adminPerms") boolean adminPerms, @JsonProperty("email") String email, @JsonProperty("userId") int userId) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.cityId = cityId;
        this.adminPerms = adminPerms;
        this.email = email;
        this.userId = userId;
    }

    /**
     * the gets the userId
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * sets the userId
     *
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * gets the userName
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the userName
     *
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets the password
     *
     * @return the password
     */
    public String getUserPassword() {
        return password;
    }

    /**
     * sets the password
     *
     * @param password the password to set
     */
    public void setUserPassword(String password) {
        this.password = password;
    }

    /**
     * gets the firstName
     *
     * @return the firstName
     */
    public String getUserFirstName() {
        return firstName;
    }

    /**
     * sets the firstName
     *
     * @param firstName the firstName to set
     */
    public void setUserFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the lastName
     *
     * @return the lastName
     */
    public String getUserLastName() {
        return lastName;
    }

    /**
     * sets the lastName
     *
     * @param lastName the lastName to set
     */
    public void setUserLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the phoneNum
     *
     * @return the phoneNum
     */
    public String getUserPhoneNum() {
        return phoneNum;
    }

    /**
     * sets the phoneNum
     *
     * @param phoneNum the phoneNum to set
     */
    public void setUserPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * gets the cityId
     *
     * @return the cityId
     */
    public int getUserCityId() {
        return cityId;
    }

    /**
     * sets the cityId
     *
     * @param cityId the cityId to set
     */
    public void setUserCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * gets the adminPerms
     *
     * @return the adminPerms
     */
    public boolean getUserAdminPerms() {
        return adminPerms;
    }

    /**
     * sets the adminPerms
     *
     * @param adminPerms the adminPerms to set
     */
    public void setUserAdminPerms(boolean adminPerms) {
        this.adminPerms = adminPerms;
    }

    /**
     * gets the email
     *
     * @return the email
     */
    public String getUserEmail() {
        return email;
    }

    /**
     * sets the email
     *
     * @param email the email to set
     */
    public void setUserEmail(String email) {
        this.email = email;
    }

    /**
     * gets the city of the user
     *
     * @return the city of the user
     */
    public Cities getUserCity() {
        return CitiesDBHelper.getCity(cityId);
    }

    /**
     * converts the Users object to a UserBean object
     *
     * @return the UserBean object
     */
    public UserBean toUserBean() {
        return new UserBean(this.userName, this.password, this.firstName, this.lastName, this.phoneNum, this.cityId,
                this.adminPerms, this.email, this.userId);
    }

    /**
     * to string method for the Users class
     *
     * @return string representation of the Users object
     */
    public String toString() {
        return "User ID: " + userId + " User Name: " + userName + " Password: " + password + " First Name: " + firstName + " Last Name: " + lastName + " Phone Number: " + phoneNum + " City: " + getUserCity().toString() + " Admin Permissions: " + adminPerms + " Email: " + email + '\n';
    }

}
