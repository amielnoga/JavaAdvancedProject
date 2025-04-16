package BackEnd.beans;

import BackEnd.DBHelper.ContactusDBHelper;
import BackEnd.Enums.ContactRequestStatus;
import BackEnd.Enums.ContactRequestType;
import BackEnd.models.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a contact request created by the user in the contact us web page for the website's managers.
 */
@Component("ContactRequestBean")
@ViewScoped
public class ContactRequestBean implements Serializable {

    private final static String URL = "http://localhost:8080";
    @Autowired
    private UserBean user;
    private ContactRequestType interestedIn;
    private String description;
    private final Date dateOfOpening;
    private ContactRequestStatus status;
    private List<ContactUs> contactRequests;

    /**
     * Default constructor initializes default values for a new contact request.
     * It sets the request status to PENDING, the opening date to the current date,
     * and the interestedIn field to ADOPTION_CONSULTATION
     */
    public ContactRequestBean() {
        this.status = BackEnd.Enums.ContactRequestStatus.PENDING;
        this.dateOfOpening = setDateOfOpening();
        this.interestedIn = BackEnd.Enums.ContactRequestType.ADOPTION_CONSULTATION;
        this.description = null;
    }

    /**
     * Sets the currently logged-in user for this bean.
     * This setter is required by JSF so the framework can inject the UserBean details to the form.
     * JSF calls this method automatically.
     *
     * @param user the currently logged-in user
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * If no user is logged in this method creates a new UserBean to represent an anonymous user.
     * This ensures that the contact form can still be filled and submitted by guests.
     * Also, initializes all the contact requests for manager's view.
     */
    @PostConstruct
    public void init() {
        if (user == null) {
            user = new UserBean();
        }
        contactRequests = ContactusDBHelper.getAllRequests();
    }

    /**
     * @return the description the user fill in the contact form
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description the user fill in the contact us form.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * if the User is Admin he can edit the contact request status.
     *
     * @param contact the contact request we need to update the status.
     */
    public void setStatus(ContactUs contact) {
        try{
        RestTemplate restTemplate = new RestTemplate();
        String pathUri;
        if (this.user.isAdmin()) {
            pathUri = URL + String.format("/contactRequests/change/%d/%s", contact.getId(), contact.getStatus());
            ResponseEntity<Void> response = restTemplate.getForEntity(pathUri, Void.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Status for #" + contact.getId() + " updated to " + contact.getStatus(), null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "an error occurred when changing the status", null));
            }
        }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
        }
    }

    /**
     * @param interestedIn The cause the user opened the contact request.
     */
    public void setInterestedIn(ContactRequestType interestedIn) {
        this.interestedIn = interestedIn;
    }

    /**
     * Returns the type of interest selected by the user in the contact request.
     *
     * @return the user's interest (e.g., adoption consultation)
     */
    public ContactRequestType getInterestedIn() {
        return this.interestedIn;
    }

    /**
     * @return the server date in order to update the contact request's opening time to the time when the user creates
     * the contact request.
     */
    public Date setDateOfOpening() {
        return new Date();
    }

    /**
     * This method saves the current user's contact request into the database.
     * If the user is admin, the method also refreshes the list of all contact requests.
     *
     * @return null to remain on the same page after submission.
     */
    public String submit() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        String pathUri = URL + "/contactRequests/add";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("email", user.getEmail());
        params.put("message", description);
        params.put("dateRequest", date);
        params.put("status", status);
        params.put("requestType", interestedIn.getLabel());
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity(pathUri, request, Void.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Your request was sent successfully", null));
                if (this.user.isAdmin()) {
                    RestTemplate restTemplateForFetching = new RestTemplate();
                    HttpEntity<Void> requestEntity = (HttpEntity<Void>) HttpEntity.EMPTY;
                    ResponseEntity<List<ContactUs>> contactListResponse = restTemplateForFetching.exchange(
                            URL + "/contactRequests/all", HttpMethod.PUT, requestEntity,
                            new ParameterizedTypeReference<List<ContactUs>>() {
                            });
                    contactRequests = contactListResponse.getBody();
                }
                return "home?faces-redirect=true";
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error occurred when submitting the form", null));
                return null;
            }
        }catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * This method returns all the contact requests from the database.
     *
     * @return list of all the contact requests
     */
    public List<ContactUs> getContactRequests() {
        return contactRequests;
    }

    /**
     * This method returns all the interested in types
     *
     * @return array of all the interested in types
     */
    public ContactRequestType[] getInterestedInTypes() {
        return ContactRequestType.values();
    }

    /**
     * This method returns all the status options
     *
     * @return array of all the status options
     * @return array of all the status options
     */
    public ContactRequestStatus[] getStatusOptions() {
        return ContactRequestStatus.values();
    }
}