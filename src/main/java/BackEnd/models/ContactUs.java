package BackEnd.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class ContactUs {
    // Attributes
    private String message;
    private Date dateRequest;
    private String status;
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String requestType;

    /**
     * Constructor for the ContactUs class with parameters.
     *
     * @param id          the ID of the contact request
     * @param message     the message of the contact request
     * @param dateRequest the date of the contact request
     * @param status      the status of the contact request
     * @param email       the email of the contact request
     * @param firstName   the first name of the contact request
     * @param lastName    the last name of the contact request
     * @param phoneNumber the phone number of the contact request
     * @param requestType the request type of the contact request
     */
    @JsonCreator
    public ContactUs(@JsonProperty("id") int id, @JsonProperty("message") String message, @JsonProperty("dateRequest") Date dateRequest, @JsonProperty("status") String status,
                     @JsonProperty("email") String email, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("requestType") String requestType) {
        this.id = id;
        this.message = message;
        this.dateRequest = dateRequest;
        this.status = status;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.requestType = requestType;
    }

    /**
     * Get the ID of the contact request.
     *
     * @return id The ID of the contact request.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the contact request.
     *
     * @param id The ID of the contact request.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the message of the contact request.
     *
     * @return message The message of the contact request.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message of the contact request.
     *
     * @param message The message of the contact request.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the date of the contact request.
     *
     * @return dateRequest The date of the contact request.
     */
    public Date getDateRequest() {
        return dateRequest;
    }

    /**
     * Set the date of the contact request.
     *
     * @param dateRequest The date of the contact request.
     */
    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    /**
     * Get the status of the contact request.
     *
     * @return status The status of the contact request.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the contact request.
     *
     * @param status The status of the contact request.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get the email of the contact request.
     *
     * @return email The email of the contact request.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the contact request.
     *
     * @param email The email of the contact request.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the first name of the contact request.
     *
     * @return firstName The first name of the contact request.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the contact request.
     *
     * @param firstName The first name of the contact request.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the contact request.
     *
     * @return lastName The last name of the contact request.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the contact request.
     *
     * @param lastName The last name of the contact request.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the phone number of the contact request.
     *
     * @return phoneNumber The phone number of the contact request.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number of the contact request.
     *
     * @param phoneNumber The phone number of the contact request.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the request type of the contact request.
     *
     * @return requestType The request type of the contact request.
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Set the request type of the contact request.
     *
     * @param requestType The request type of the contact request.
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * Returns a string representation of the ContactUs object.
     *
     * @return A string representation of the ContactUs object.
     */
    public String toString() {
        return "Message: " + message + "\nDate: " + dateRequest + "\nStatus: " + status;
    }
}
