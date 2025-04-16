package BackEnd.beans;

import BackEnd.Enums.PetSize;
import BackEnd.Enums.PetType;
import BackEnd.models.Pets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

/**
 * This class represents a pet in Adopet registry.
 */
@Component("PetBean")
@ViewScoped
public class PetBean implements Serializable {
    private final static String URL = "http://localhost:8080";

    private PetSize size;
    private PetType type;

    private Boolean isAdopted;
    private Date adoptionDate;
    private int id;
    private String name;
    private String gender;
    private String birthDate;
    private Boolean isNeutered;
    private String shortDescription;
    private String longDescription;
    private String imageBase64;
    private String imageType;
    private int minAge = 0;
    private int maxAge = 20;
    private String district = "0";

    //    @Autowired
    private UserBean owner;
    private Boolean isActive;
    private PetBean selectedPet;
    private boolean filterSubmitted = false;

    private List<String> filterRequest = new ArrayList<>();
    private List<PetBean> filteredData = new ArrayList<>();
    private boolean clearOnReload = false;

    /**
     * Default constructor for PetBean.
     * Initializes a new pet object with default values.
     **/
    public PetBean() {
        this.id = -1;
        this.name = null;
        this.gender = null;
        this.birthDate = null;
        this.isNeutered = false;
        this.shortDescription = null;
        this.longDescription = null;
        this.imageBase64 = "null";
        this.imageType = "null";
        this.isActive = true;
        this.type = null;
        this.size = null;
        this.isAdopted = false;
        this.adoptionDate = null;
        this.minAge = Integer.MIN_VALUE;
        this.maxAge = Integer.MAX_VALUE;
        this.district = null;
    }

    /**
     * Constructs a PetBean with the given attributes.
     **/
    public PetBean(int id, String name, String gender, String birthDate, boolean isNeutered, String shortDescription,
                   String longDescription, Boolean isAdopted, String imageBase64, String imageType, String type, String size, UserBean owner) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.isNeutered = isNeutered;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageBase64 = imageBase64;
        this.imageType = imageType;
        this.type = PetType.getEnum(type);
        this.size = PetSize.getEnum(size);
        this.owner = owner;
        this.isActive = true;
        this.isAdopted = isAdopted;
    }

    /**
     * Constructs a PetBean with the given UserBean as its owner.
     *
     * @param owner the currently logged-in user
     */
    @Autowired
    public PetBean(UserBean owner) {
        this.owner = owner;
    }

    /**
     * @return The pet's id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The pet's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The gender of the pet.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * @param gender the gender of the pet.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return true if the pet is neutered, false otherwise.
     */
    public Boolean getIsNeutered() {
        return isNeutered;
    }

    /**
     * @param neutered true if the pet is neutered, false otherwise.
     */
    public void setIsNeutered(Boolean neutered) {
        this.isNeutered = neutered;
    }


    /**
     * gets the status of the pet
     *
     * @return if the pet is adopted or not
     */
    public Boolean getIsAdopted() {
        return this.isAdopted;
    }

    /**
     * sets the status of Adopted for the pet
     *
     * @param adopted if the pet is adopted or not
     */
    public void setIsAdopted(Boolean adopted) {
        this.isAdopted = adopted;
    }

    /**
     * @return a short description of the pet.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription a short description of the pet.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * @return a long description of the pet.
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @param longDescription a long description of the pet.
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * @return the pet's age.
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * @param birthDate The date the pet was born in order to display the pet age.
     */
    public void setBirthDate(String birthDate) {
        Date birthDayFormat = Date.valueOf(birthDate);
        Date currentDate = new Date(System.currentTimeMillis());
        Date twentyYearsAgo = new Date(currentDate.getTime() - (20L * 365 * 24 * 60 * 60 * 1000));
        long ageInMillis = currentDate.getTime() - birthDayFormat.getTime();
        long ageBeforeTwentyYears = birthDayFormat.getTime() - twentyYearsAgo.getTime();

        if (ageInMillis < 0 || ageBeforeTwentyYears < 0)
            this.birthDate = null;
        else {
            this.birthDate = birthDate;
        }
    }

    /**
     * Returns the minimum age of the pet used for filtering search results
     * in the adoption board.
     *
     * @return the current minimum age value used for filtering.
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Sets the minimum pet age.
     * The value is only updated if it is between 0 and 20.
     *
     * @param minAge the minimum pet age to set.
     */
    public void setMinAge(int minAge) {
        if (minAge >= 0 && minAge < 20)
            this.minAge = minAge;
    }

    /**
     * Returns the maximum age of the pet used for filtering search results
     * in the adoption board.
     *
     * @return the current maximum age value used for filtering.
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the maximum age of the pet used to filter search results
     * in the adoption board.
     * The value is only updated if it is between 0 (inclusive) and 20 (exclusive),
     * and greater than the current minimum age.
     *
     * @param maxAge the maximum pet age to use for filtering.
     */
    public void setMaxAge(int maxAge) {
        if (minAge >= 0 && minAge < 20 && maxAge > minAge)
            this.maxAge = maxAge;
    }

    /**
     * @return The pet's owner.
     */
    public UserBean getOwner() {
        return owner;
    }

    /**
     * @param owner The pet's owner.
     */
    public void setOwner(UserBean owner) {
        this.owner = owner;
    }

    /**
     * @return The pet's image.
     */
    public String getImageBase64() {
        return this.imageBase64;
    }

    /**
     * @param imageBase64 The pet's image.
     */
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    /**
     * @return The pet's image type.
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType The pet's image type.
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * Returns a URI for displaying the image.
     *
     * @return a URI that can be used.
     */

    public String getImageUri() {
        if (imageBase64.equals("null") || imageType.equals("null")) {
            return "/resources/images/logo_only.png";
        }
        return "data:" + this.imageType + ";base64," + this.imageBase64;
    }

    /**
     * gets the district of the pet
     *
     * @return The pet's district.
     */
    public String getDistrict() {

        return district;
    }

    /**
     * sets the district of the pet
     *
     * @param district district of the pet
     */
    public void setDistrict(String district) {
        this.district = district;
    }


    /**
     * sets the filter for the pet list extraction
     */
    public void setFilterRequest(String category) {
        this.filterRequest.add(category);
    }

    /**
     * @return The filter for the pet list extraction
     */
    public List<String> getFilterRequest() {
        return this.filterRequest;
    }


    /**
     * creates a map to send to the server as Rest request
     *
     * @param id               the id of the pet
     * @param name             the name of the pet
     * @param birthDate        the birthdate of the pet
     * @param gender           the gender of the pet
     * @param isActive         the status of the pet
     * @param castration       the castration status of the pet
     * @param shortDescription the short description of the pet
     * @param longDescription  the long description of the pet
     * @param type             the type of the pet
     * @param size             the size of the pet
     * @param ownerId          the id of the pet owner
     * @param imageB64         the image of the pet in base64 format
     * @param imageType        the image type of the pet
     * @param isAdopted        the adoption status of the pet
     * @param adoptionDate     the adoption date of the pet
     * @return map of the pet data
     */
    private Map<String, Object> createRestMap(int id, String name, Date birthDate, String gender, boolean isActive, boolean castration, String shortDescription, String longDescription, String type, String size, int ownerId, String imageB64, String imageType, boolean isAdopted, Date adoptionDate) {
        Map<String, Object> petData = new HashMap<>();
        petData.put("id", id);
        petData.put("name", name);
        petData.put("birthDay", birthDate);
        petData.put("gender", gender);
        petData.put("status", isActive);
        petData.put("castration", castration);
        petData.put("shortDescription", shortDescription);
        petData.put("longDescription", longDescription);
        petData.put("type", type);
        petData.put("size", size);
        petData.put("ownerId", ownerId);
        petData.put("image", imageB64);
        petData.put("imageType", imageType);
        petData.put("isAdopted", isAdopted);
        petData.put("adoptionDate", adoptionDate);
        return petData;
    }

    /**
     * submits the pet data to the server
     *
     * @return the page to redirect to
     */
    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (this.birthDate == null) { // check if the birthdate fine
                context.addMessage("addPetForm:birthDate", new FacesMessage(FacesMessage.SEVERITY_ERROR, "please " +
                        "check the date you selected, it does not meet most pets lifespan", null));
                return null;
            }
            // create a Rest request to add the pet
            Map<String, Object> petData = createRestMap(id, name, Date.valueOf(birthDate), gender, true, isNeutered,
                    shortDescription, longDescription, this.type.getLabel(), size.getLabel(), this.owner.getId(),
                    "null", "null", false, null);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(petData, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(URL + "/pets/add", request, String.class);
            context.getExternalContext().getFlash().setKeepMessages(true);

            // check if the response is ok
            if (response.getStatusCode() == HttpStatus.OK) {
                return "setupPhoto?faces-redirect=true&id=" + response.getBody();
            } else {
                // if the response is not ok, show an error message
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The pet could not" +
                        "be added. Please check your data and try again.", null));
                return null;
            }
        } catch (Exception e) {
            // if there is an error, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The pet could not be " +
                    "added due to a server connection issue.", null));
            return null;
        }
    }

    /**
     * updates the pet data to the server
     *
     * @return the page to redirect to
     */
    public String updatePet() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
        if (this.birthDate == null) { // check if the birthdate fine
            context.addMessage("myPetsForm:birthDate", new FacesMessage(FacesMessage.SEVERITY_ERROR, "please " +
                    "check the Date you selected it does not meet most pets lifespan", null));
            return null;
        }
        // create a Rest request to update the pet
        Date adoptionDate = null;
        if (isAdopted)
            adoptionDate = new java.sql.Date(System.currentTimeMillis());
        Map<String, Object> petData = createRestMap(id, name, Date.valueOf(birthDate), gender, true, isNeutered, shortDescription, longDescription, type.getLabel(), size.getLabel(), this.owner.getId(), "null", "null", isAdopted, adoptionDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(petData, headers);
        ResponseEntity<String> response = restTemplate.exchange(String.format(URL + "/pets/update/%d", id), HttpMethod.PUT, request, String.class);
        // check if the response is ok
        if (response.getStatusCode() == HttpStatus.OK) {
            this.clearOnReload = true;
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The pet has been updated", null));
            return "myPets?faces-redirect=true";
        } else {
            // if the response is not ok, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The pet could not" +
                    "be added. Please check your data and try again.", null));
            return null;
        }
        } catch (Exception e) {
            // if there is an error, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The pet could not be " +
                    "added due to a server connection issue.", null));
            return null;
        }
    }

    /**
     * redirects to the photo setup page with the selected pet id
     *
     * @return the page to redirect to
     */
    public String updatePhoto() {
        return "setupPhoto?faces-redirect=true&id=" + this.id;
    }


    /**
     * deletes the pet from the database
     *
     * @return the page to redirect to
     */
    public String deletePet() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
        context.getExternalContext().getFlash().setKeepMessages(true);
        // create a Rest request to delete the pet
        RestTemplate restTemplate = new RestTemplate();
        String pathUri = URL + String.format("/pets/delete/%d", id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                pathUri,
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );

        // check if the response is ok
        if (response.getStatusCode() == HttpStatus.OK) {
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The pet was deleted successfully", null));
            return viewId + "?faces-redirect=true";
        } else {
            // if the response is not ok, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The pet could not be deleted", null));
            return null;
        }
        }catch (Exception e) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "an error with the connection occurred when trying to connect to the server.", null));
            return null;
        }
    }

    /**
     * gets the filtered data from the server
     *
     * @return the filtered data
     */
    public List<PetBean> getFilteredData() {
        return filteredData;
    }

    /**
     * sets the filtered data from the server
     *
     * @param filteredData the filtered data to set
     */
    public void setFilteredData(List<PetBean> filteredData) {
        this.filteredData = filteredData;
    }


    /**
     * gets the filtered pets from the server with the rest connection
     */
    public void getFilteredPets() {
        // parses the filter request to a map to send to the Rest request
        Map<String, Object> filterParams = new HashMap<>();
        for (String filter : filterRequest) {
            String[] filterParts = filter.split(":");
            if (filterParts[0].equals("type")) {
                filterParams.put("type", filterParts[1]);
            } else if (filterParts[0].equals("gender")) {
                filterParams.put("gender", filterParts[1]);
            } else if (filterParts[0].equals("castration")) {
                boolean bool = Boolean.parseBoolean(filterParts[1]);
                int value = bool ? 1 : 0;
                filterParams.put("castration", value);
            } else if (filterParts[0].equals("isAdopted")) {
                boolean bool = Boolean.parseBoolean(filterParts[1]);
                int value = bool ? 1 : 0;
                filterParams.put("isAdopted", value);
            } else if (filterParts[0].equals("size")) {
                filterParams.put("size", filterParts[1]);
            } else if (filterParts[0].equals("ownerId")) {
                filterParams.put("owner_id", Integer.valueOf(filterParts[1]));
            } else if (filterParts[0].equals("status")) {
                boolean bool = Boolean.parseBoolean(filterParts[1]);
                int value = bool ? 1 : 0;
                filterParams.put("status", value);
            } else if (filterParts[0].equals("ageLessThan")) {
                filterParams.put("ageLessThan", Integer.valueOf(filterParts[1]));
            } else if (filterParts[0].equals("ageMoreThan")) {
                filterParams.put("ageMoreThan", Integer.valueOf(filterParts[1]));
            } else if (filterParts[0].equals("district")) {
                filterParams.put("district", filterParts[1]);
            }


        }
        // creates the Rest request to get the filtered pets
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/pets/filter");
        for (Map.Entry<String, Object> entry : filterParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        String uri = builder.toUriString();
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Pets[]> response = restTemplate.getForEntity(uri, Pets[].class);

            // extracts the pets from the response
            Pets[] petsArray = response.getBody();
            List<Pets> filteredPets = Arrays.asList(petsArray);
            List<PetBean> filteredPetBeans = new ArrayList<>();
            for (Pets pet : filteredPets) {
                PetBean petBean = new PetBean(pet.getId(), pet.getName(), pet.getGender(), pet.getBirthDay().toString(), pet.getCastration(), pet.getShortDescription(), pet.getLongDescription(), pet.getIsAdopted(), pet.getImage(), pet.getImageType(), pet.getType(), pet.getSize(), pet.getOwner().toUserBean());
                filteredPetBeans.add(petBean);
            }
            this.setFilteredData(filteredPetBeans);
        }
        catch (Exception e) {
            this.filteredData.clear();
        }
    }

    /**
     * gets the age of the pet
     *
     * @return the age of the pet as string
     */
    public String getAge() {
        Date birthDate = Date.valueOf(this.birthDate); // gets the birthdate of the pet
        Date currentDate = new Date(System.currentTimeMillis());
        long ageInMillis = currentDate.getTime() - birthDate.getTime();
        long ageInDays = ageInMillis / (1000 * 60 * 60 * 24); // converts the age to days
        return String.valueOf(ageInDays / 365); // converts the age to years
    }

    /**
     * gets the type of the pet
     *
     * @return returns the type of the pet
     */
    public PetType getType() {
        return this.type;
    }

    /**
     * sets the type of the pet
     *
     * @param type the type of the pet
     */
    public void setType(PetType type) {
        this.type = type;
    }

    /**
     * gets the size of the pet
     *
     * @return gets the size of the pet
     */
    public PetSize getSize() {
        return size;
    }

    /**
     * sets the size of the pet
     *
     * @param size the size of the pet
     */
    public void setSize(PetSize size) {
        this.size = size;
    }


    /**
     * gets the adoption date of the pet
     *
     * @return the adoption date of the pet
     */
    public java.sql.Date getAdoptionDate() {
        return adoptionDate;
    }

    /**
     * sets the adoption date of the pet
     *
     * @param adoptionDate the adoption date of the pet
     */
    public void setAdoptionDate(java.sql.Date adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    /**
     * sets the filter request for the pet when we want to show all the pets that are adopted
     * is triggers the data for the filter request.
     */
    public void InitFilterRequestAdoptedTrue() {
        if (!FacesContext.getCurrentInstance().isPostback()) { // if the filter is not cleared, clear the filtered data it happens only when we are not needing to show filtered data before
            filteredData.clear();
        }
        filterRequest.clear();

        String filter = "isAdopted:true"; // set the filter request to show all the pets that are adopted
        this.setFilterRequest(filter);
        filter = "status:true";
        this.setFilterRequest(filter);
    }

    /**
     * sets the filter request for the pet when we want to show all the pets that are not adopted
     * is triggers the data for the filter request.
     */
    public void InitFilterRequestAdoptedFalse() {
        filterRequest.clear(); // clear the filter request
        String filter = "isAdopted:false"; // set the filter request to show all the pets that are not adopted
        this.setFilterRequest(filter);
        filter = "status:true";
        this.setFilterRequest(filter);
        if (!FacesContext.getCurrentInstance().isPostback() && !clearOnReload) { // if the filter is not cleared, clear the filtered data it happens only when we are not needing to show filtered data before
            filteredData.clear();
            getFilteredPets();
        }
        clearOnReload = false;
    }

    /**
     * sets the filter request for the pet when we want to show all the pets that are owned by the user
     * is triggers the data for the filter request.
     */
    public void InitFilterRequestMyPets() {
        if (!FacesContext.getCurrentInstance().isPostback() && !clearOnReload) { // if the filter is not cleared, clear the filtered data it happens only when we are not needing to show filtered data before
            filteredData.clear();
        }
        clearOnReload = false;
        filterRequest.clear(); // sets the filter request to show all the pets that are owned by the user
        String filter = "ownerId:" + owner.getId();
        this.setFilterRequest(filter);
        filter = "status:true";
        this.setFilterRequest(filter);
    }

    /**
     * Command used on the home Button when we want to redirect to the board page with a selected type of pet
     *
     * @param type the type of pet to filter
     * @return the redirect to the board page
     */
    public String makeFilterRequest(String type) {
        filterSubmitted = true;
        filterRequest.clear();
        if (!type.equals("All")) { // if the type is not all, set the filter request to show all the pets that are of the selected type
            String filter = String.format("type:%s", type);
            this.setFilterRequest(filter);
            filter = "isAdopted:false";
            this.setFilterRequest(filter);
            this.getFilteredPets();
            clearOnReload = true;
        }
        return "board?faces-redirect=true"; // redirect to the board page
    }

    /**
     * advanced filter request for the pets, parses the data to a map and sends it to treat the data request
     * this function makes the filtered data to be filtered as we wanted to/
     */
    public void makeAdvancedFilterRequest() {
        filteredData.clear();
        selectedPet = null;
        filterSubmitted = true;
        // checks the filter request for each condition
        if (this.type != null) {
            String filter = String.format("type:%s", this.type.getLabel());
            this.setFilterRequest(filter);
        }
        if (this.size != null) {
            String filter = String.format("size:%s", size.getLabel());
            this.setFilterRequest(filter);
        }
        if (this.gender != null && !this.gender.equals("null")) {
            String filter = String.format("gender:%s", this.gender);
            this.setFilterRequest(filter);
        }
        if (this.minAge != Integer.MIN_VALUE) {
            String filter = String.format("ageMoreThan:%d", this.minAge);
            this.setFilterRequest(filter);
        }
        if (this.maxAge != Integer.MAX_VALUE) {
            String filter = String.format("ageLessThan:%d", this.maxAge);
            this.setFilterRequest(filter);
        }
        if (!this.district.equals("0")) {
            String filter = String.format("district:%s", this.district);
            this.setFilterRequest(filter);
        }
        if (this.isNeutered != null) {
            String filter = String.format("castration:%b", this.isNeutered);
            this.setFilterRequest(filter);
        }
        // send the filter request to the server and init the filtered data
        this.getFilteredPets();
        filterRequest.clear(); // clears the filter request
    }

    /**
     * gets the selected pet from the list
     *
     * @return the selected pet
     */
    public PetBean getSelectedPet() {
        return selectedPet;
    }

    /**
     * sets the selected pet from the list
     *
     * @param selectedPet the selected pet
     */
    public void setSelectedPet(PetBean selectedPet) {
        this.selectedPet = selectedPet;
    }


    /**
     * clears the selected pet from the list
     */
    public void clearSelectedPet() {
        this.selectedPet = null;
    }

    /**
     * clears the selected pet from the list when the page is loaded
     */
    public void clearSelectedPetOnLoad() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (!ctx.isPostback())
            this.selectedPet = null;
    }


    /**
     * checks if the filter is submitted
     *
     * @return filter submitted
     */
    public boolean getFilterSubmitted() {
        return filterSubmitted;
    }

    /**
     * sets the filter submitted
     *
     * @param filterSubmitted the filter submitted
     */
    public void setFilterSubmitted(boolean filterSubmitted) {
        this.filterSubmitted = filterSubmitted;
    }

    /**
     * Returns all available pet size options defined in the PetSize enum.
     * used to populate UI component dropdown buttons
     *
     * @return an array of all PetSize enum
     */
    public PetSize[] getSizeOptions() {
        return PetSize.values();
    }

    /**
     * Returns all available pet type options defined in the PetType enum.
     * used to populate UI component dropdown buttons
     *
     * @return an array of all PetType enum
     */
    public PetType[] getTypeOptions() {
        return PetType.values();
    }
}
