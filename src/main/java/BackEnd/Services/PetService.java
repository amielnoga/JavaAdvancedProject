package BackEnd.Services;

import BackEnd.DBHelper.PetsDBHelper;
import BackEnd.models.Pets;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class PetService {

    /**
     * get all the filtered pets
     *
     * @param params the parameters to filter the pets
     * @return the list of pets
     */
    public List<Pets> getPetsFiltered(Map<String, Object> params) {
        return PetsDBHelper.getFilteredPets(params);

    }

    /**
     * get Pet by id
     *
     * @param petId the id of the pet
     * @return the pet
     */
    public Pets getPetById(int petId) {
        return PetsDBHelper.getPet(petId);
    }

    /**
     * add a pet to the database
     *
     * @param pet the pet to add
     * @return the id of the pet
     */
    public int addPet(Pets pet) {
        return PetsDBHelper.addPet(pet);
    }

    /**
     * update a pet in the database
     *
     * @param petId the id of the pet to update
     * @param pet   the pet to update
     */
    public void updatePet(int petId, Pets pet) {
        PetsDBHelper.updatePet(petId, pet);
    }

    /**
     * upload a pet image to the database
     *
     * @param petId    the id of the pet
     * @param file     the file to upload
     * @param mimeType the mime type of the file
     * @return true if the upload was successful, false otherwise
     */
    public boolean uploadPetImage(int petId, MultipartFile file, String mimeType) {
        Pets pet = PetsDBHelper.getPet(petId); // get the pet by id

        if (pet == null) {
            return false;
        }

        try {// convert the file to a base64 string
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            pet.setImage(base64);
            pet.setImageType(mimeType);
            PetsDBHelper.changePhoto(petId, pet);
        } catch (IOException e) {
            return false; // if there was an error uploading the file
        }
        return true; // if the upload was successful
    }

    /**
     * delete a pet from the database
     *
     * @param petId the id of the pet to delete
     */
    public void deletePet(int petId) {
        PetsDBHelper.deletePet(petId);
    }
}
