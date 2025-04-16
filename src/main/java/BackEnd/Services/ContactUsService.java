package BackEnd.Services;

import BackEnd.DBHelper.ContactusDBHelper;
import BackEnd.models.ContactUs;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactUsService {

    /**
     * addContactRequest method is used to add a new contact request to the database.
     *
     * @param contactUs The contact request object containing the details of the request.
     */
    public void addContactRequest(ContactUs contactUs) {
        ContactusDBHelper.addRequest(contactUs.getFirstName(), contactUs.getLastName(), contactUs.getPhoneNumber(), contactUs.getEmail(), contactUs.getMessage(), contactUs.getDateRequest(), contactUs.getStatus(), contactUs.getRequestType());
    }

    /**
     * updateContactStatusRequest method is used to update the status of a contact request in the database.
     *
     * @param id     The ID of the contact request to be updated.
     * @param status The new status of the contact request.
     */
    public void updateContactStatusRequest(int id, String status) {
        ContactusDBHelper.updateRequestStatus(id, status);
    }

    /**
     * getAllContactRequests method is used to retrieve all contact requests from the database.
     *
     * @return A list of all contact requests.
     */
    public List<ContactUs> getAllContactRequests() {
        return ContactusDBHelper.getAllRequests();
    }
}
