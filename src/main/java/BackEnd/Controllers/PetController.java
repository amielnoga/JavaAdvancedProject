package BackEnd.Controllers;

import BackEnd.Services.PetService;
import BackEnd.models.Pets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pets")
public class PetController {
    @Autowired
    private PetService petService; // petService is a service class that handles the Database for pets

    /**
     * getPetById method is used to retrieve a pet by its ID.
     *
     * @param id the id of the pet to be retrieved
     * @return the pet object with the specified ID
     */
    @GetMapping("/{id}")
    public Pets getPetById(@PathVariable int id) {
        return petService.getPetById(id);
    }

    /**
     * getFilteredPets method is used to retrieve all pets from the database that have the values of the filter.
     *
     * @param filterParams the filter parameters to be used for filtering the pets
     * @return list of pets that match the filter parameters
     */
    @GetMapping("/filter")
    public List<Pets> getFilteredPets(@RequestParam Map<String, Object> filterParams) {
        return petService.getPetsFiltered(filterParams);
    }

    /**
     * addPet method is used to add a new pet to the database.
     *
     * @param pet the pet object to be added
     * @return the ID of the newly added pet
     */
    @PostMapping("/add")
    public int addPet(@RequestBody Pets pet) {
        // calling the addPet method from the petService class
        return petService.addPet(pet);

    }

    /**
     * updatePet method is used to update the data of a pet.
     *
     * @param id  the id of the pet to be updated
     * @param pet the pet object with the new data
     */
    @PutMapping("/update/{id}")
    public void updatePet(@PathVariable int id, @RequestBody Pets pet) {
        petService.updatePet(id, pet);
    }

    /**
     * uploadPet method is used to upload a file for a pet.
     *
     * @param id the id of the pet to upload the file for
     */
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPet(@PathVariable int id, @RequestParam("file") MultipartFile file, @RequestParam("mimeType") String mimeType) {
        boolean result = petService.uploadPetImage(id, file, mimeType);
        if (result) { // if the file is uploaded successfully
            return ResponseEntity.ok("File uploaded successfully for pet with id: " + id);
        }
        // if the file is not uploaded successfully
        return ResponseEntity.status(500).body("Failed to upload file for pet with id: " + id);

    }

    /**
     * deletePet method is used to delete a pet from the database.
     *
     * @param petId the id of the pet to be deleted
     */
    @DeleteMapping("/delete/{petId}")
    public void deletePet(@PathVariable int petId) {
        petService.deletePet(petId);
    }
}
