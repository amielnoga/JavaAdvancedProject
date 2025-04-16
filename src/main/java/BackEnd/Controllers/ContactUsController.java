package BackEnd.Controllers;

import BackEnd.Services.ContactUsService;
import BackEnd.models.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactRequests")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService; // Autowire the ContactUsService

    /**
     * Rest connection that adds a new contact request to the database when receiving a GET request.
     *
     * @param contactUs the contact request to add
     */
    @PostMapping("/add")
    public void addContactRequest(
            @RequestBody ContactUs contactUs) {
        contactUsService.addContactRequest(contactUs);
    }

    /**
     * Rest connection the changes the status of a contact request when receiving a GET request.
     *
     * @param contactId the ID of the contact request
     * @param status    the new status
     */
    @GetMapping("/change/{contactId}/{status}")
    public void changeStatus(@PathVariable int contactId, @PathVariable String status) {
        contactUsService.updateContactStatusRequest(contactId, status);
    }

    /**
     * Rest connection that sends all the contact requests in the database when receiving a PUT request.
     *
     * @return List of ContactUs objects
     */
    @PutMapping("/all")
    public List<ContactUs> getAllContacts() {
        // Call the service method to get all contact requests
        return contactUsService.getAllContactRequests();
    }
}
