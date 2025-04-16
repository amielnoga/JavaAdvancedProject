package BackEnd.models;

public class Cities {
    // Attributes
    private int id;
    private String name;
    private String district;

    /**
     * Default constructor for the Cities class.
     */
    public Cities() {
    }

    /**
     * Constructor for the Cities class with parameters.
     *
     * @param id       The ID of the city.
     * @param name     The name of the city.
     * @param district The district of the city.
     */
    public Cities(int id, String name, String district) {
        this.id = id;
        this.name = name;
        this.district = district;
    }

    /**
     * Get the name of the city.
     *
     * @return The name of the city.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the district of the city.
     *
     * @return The district of the city.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Set the name of the city.
     *
     * @param name The name of the city.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the district of the city.
     *
     * @param district The district of the city.
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Get the ID of the city.
     *
     * @return The ID of the city.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the city.
     *
     * @param id The ID of the city.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * toString method to return a string representation of the city.
     *
     * @return A string representation of the city.
     */
    public String toString() {
        return "City: " + name + " District: " + district;
    }
}
