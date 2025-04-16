package BackEnd.models;

import BackEnd.DBHelper.UserDBHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Pets {
    private Date adoptionDate;
    private boolean isAdopted;
    private String name;
    private Date birthDay;
    private String type;
    private String size;
    private String gender;
    private boolean status;
    private boolean castration;
    private String shortDescription;
    private String longDescription;
    private int ownerId;
    private String image;
    private String imageType;
    private int id;


    /**
     * Constructor for the Pets class.
     *
     * @param id               the ID of the pet
     * @param name             the name of the pet
     * @param birthDay         the birthday of the pet
     * @param gender           the gender of the pet
     * @param status           the status of the pet
     * @param castration       the castration status of the pet
     * @param shortDescription the short description of the pet
     * @param longDescription  the long description of the pet
     * @param type             the type of the pet
     * @param size             the size of the pet
     * @param isAdopted        the adoption status of the pet
     * @param adoptionDate     the adoption date of the pet
     * @param ownerId          the ID of the pet's owner
     * @param image            the image of the pet
     * @param imageType        the type of the image
     */
    @JsonCreator
    public Pets(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("birthDay") Date birthDay,
                @JsonProperty("gender") String gender, @JsonProperty("status") boolean status, @JsonProperty("castration") boolean castration,
                @JsonProperty("shortDescription") String shortDescription, @JsonProperty("longDescription") String longDescription,
                @JsonProperty("type") String type, @JsonProperty("size") String size, @JsonProperty("isAdopted") boolean isAdopted, @JsonProperty("adoptionDate") Date adoptionDate, @JsonProperty("ownerId") int ownerId, @JsonProperty("image") String image, @JsonProperty("imageType") String imageType) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.type = type;
        this.size = size;
        this.gender = gender;
        this.status = status;
        this.castration = castration;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.isAdopted = isAdopted;
        this.adoptionDate = adoptionDate;
        this.ownerId = ownerId;
        this.image = image;
        this.imageType = imageType;
    }

    /**
     * gets the name of the pet
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the pet
     *
     * @param name the name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the birthday of the pet
     *
     * @return the birthday of the pet
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * sets the birthday of the pet
     *
     * @param birthDay the birthday of the pet
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * gets the type of the pet
     *
     * @return the type of the pet
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of the pet
     *
     * @param type the type of the pet
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets the size of the pet
     *
     * @return the size of the pet
     */
    public String getSize() {
        return size;
    }

    /**
     * sets the size of the pet
     *
     * @param size the size of the pet
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * gets the gender of the pet
     *
     * @return the gender of the pet
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * sets the gender of the pet
     *
     * @param gender the gender of the pet to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * gets the status of the pet
     *
     * @return the status of the pet
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * sets the status of the pet
     *
     * @param status the status of the pet
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * gets the castration status of the pet
     *
     * @return the castration status of the pet
     */
    public boolean getCastration() {
        return castration;
    }

    /**
     * sets the castration status of the pet
     *
     * @param castration the castration status of the pet
     */
    public void setCastration(boolean castration) {
        this.castration = castration;
    }

    /**
     * gets the short description of the pet
     *
     * @return the short description of the pet
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * sets the short description of the pet
     *
     * @param shortDescription the short description of the pet
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * gets the long description of the pet
     *
     * @return the long description of the pet
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * sets the long description of the pet
     *
     * @param longDescription the long description of the pet
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * gets the image of the pet
     *
     * @return the image of the pet
     */
    public String getImage() {
        return image;
    }

    /**
     * sets the image of the pet
     *
     * @param image the image of the pet
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * gets the owner ID of the pet
     *
     * @return the owner ID of the pet
     */
    public int getPetsOwnerId() {
        return ownerId;
    }

    /**
     * sets the owner ID of the pet
     *
     * @param ownerId the owner ID of the pet
     */
    public void setPetsOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * gets the owner of the pet
     *
     * @return the owner of the pet
     */
    public Users getOwner() {
        return UserDBHelper.getUser(ownerId);
    }

    /**
     * sets the id of the pet
     *
     * @return the id of the pet
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the pet
     *
     * @param id the id of the pet
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * the toString method of the pet
     *
     * @return the string representation of the pet
     */
    public String toString() {
        return "Pet name: " + name + " birth day: " + birthDay + " gender: " + gender + " status: " + status + " castration: " + castration + " shortDescription: " + shortDescription + " longDescription: " + longDescription + " species: " + type + " size: " + size + "\nOwner:\n" + getOwner().toString();
    }

    /**
     * gets the adoption date of the pet
     *
     * @return the adoption date of the pet
     */
    public Date getAdoptionDate() {
        return adoptionDate;
    }

    /**
     * sets the adoption date of the pet
     *
     * @param adoptionDate the adoption date of the pet
     */
    public void setAdoptionDate(Date adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    /**
     * gets the adoption status of the pet
     *
     * @return the adoption status of the pet
     */
    public Boolean getIsAdopted() {
        return isAdopted;
    }

    /**
     * sets the adoption status of the pet
     *
     * @param adopted the adoption status of the pet
     */
    public void setIsAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    /**
     * gets the image type of the pet
     *
     * @return the image type of the pet
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * sets the image type of the pet
     *
     * @param imageType the image type of the pet
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
