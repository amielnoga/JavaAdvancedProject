package BackEnd.DBHelper;

import BackEnd.models.Pets;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetsDBHelper {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/DataBases.db";

    /**
     * Creates a new SQLite database connection and creates the Pets table if it does not exist.
     */
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) { // Create a connection to the database
            String sql = "CREATE TABLE IF NOT EXISTS Pets (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " name TEXT NOT NULL,\n"
                    + " birthDay date NOT NULL,\n"
                    + " gender TEXT NOT NULL,\n"
                    + " status BOOL NOT NULL,\n"
                    + " castration BOOL NOT NULL,\n"
                    + " shortDescription TEXT NOT NULL,\n"
                    + " longDescription TEXT NOT NULL,\n"
                    + " type TEXT NOT NULL,\n"
                    + " size TEXT NOT NULL,\n"
                    + " isAdopted BOOL NOT NULL,\n"
                    + "adoptionDate date,\n"
                    + " image TEXT NOT NULL,\n"
                    + "imageType TEXT NOT NULL,\n"
                    + "owner_id INTEGER NOT NULL,\n"
                    + "    FOREIGN KEY (owner_id) REFERENCES Users(id)"
                    + ");";
            stmt.execute(sql); // update the table
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new pet to the Pets table.
     *
     * @param name             name of the pet
     * @param birthDay         birthday of the pet
     * @param gender           gender of the pet
     * @param status           status of the pet
     * @param castration       castration status of the pet
     * @param shortDescription short description of the pet
     * @param longDescription  long description of the pet
     * @param type             type of the pet
     * @param size             size of the pet
     * @param isAdopted        adoption status of the pet
     * @param adoptionDate     adoption date of the pet
     * @param image            image of the pet
     * @param imageType        image type of the pet
     * @param owner_id         owner ID of the pet
     * @return true if the pet was added successfully, false otherwise
     */
    public static boolean addPet(String name, Date birthDay, String gender, boolean status,
                                 boolean castration, String shortDescription, String longDescription, String type,
                                 String size, boolean isAdopted, Date adoptionDate, String image, String imageType, int owner_id) {
        try (Connection conn = DriverManager.getConnection(DB_URL); // Create a connection to the database
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Pets (name, birthDay, gender, " +
                     "status, castration, shortDescription, longDescription,type,size, isAdopted, adoptionDate ,image,imageType, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?, ? ,?, ?, ?)")) { // query to insert a new pet
            prepstmt.setString(1, name);
            prepstmt.setDate(2, birthDay);
            prepstmt.setString(3, gender);
            prepstmt.setBoolean(4, status);
            prepstmt.setBoolean(5, castration);
            prepstmt.setString(6, shortDescription);
            prepstmt.setString(7, longDescription);
            prepstmt.setString(8, type);
            prepstmt.setString(9, size);
            prepstmt.setBoolean(10, isAdopted);
            prepstmt.setDate(11, adoptionDate);
            prepstmt.setString(12, image);
            prepstmt.setString(13, imageType);
            prepstmt.setInt(14, owner_id);
            prepstmt.executeUpdate(); // update the table
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Adds a new pet to the Pets table and returns the generated ID.
     *
     * @param pet the Pets object to be added.
     * @return the generated ID of the added pet, or -1 if insertion fails
     */
    public static int addPet(Pets pet) {
        int generatedId = -1; // Default value if insertion fails
        try (Connection conn = DriverManager.getConnection(DB_URL); // Create a connection to the database
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Pets (name, birthDay, gender, " +
                     "status, castration, shortDescription, longDescription,type,size, isAdopted, adoptionDate ,image, imageType, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?, ? ,?, ?, ?)")) { // query to insert a new pet
            prepstmt.setString(1, pet.getName());
            prepstmt.setDate(2, pet.getBirthDay());
            prepstmt.setString(3, pet.getGender());
            prepstmt.setBoolean(4, pet.getStatus());
            prepstmt.setBoolean(5, pet.getCastration());
            prepstmt.setString(6, pet.getShortDescription());
            prepstmt.setString(7, pet.getLongDescription());
            prepstmt.setString(8, pet.getType());
            prepstmt.setString(9, pet.getSize());
            prepstmt.setBoolean(10, pet.getIsAdopted());
            prepstmt.setDate(11, pet.getAdoptionDate());
            prepstmt.setString(12, pet.getImage());
            prepstmt.setString(13, pet.getImageType());
            prepstmt.setInt(14, pet.getPetsOwnerId());

            int affectedRows = prepstmt.executeUpdate();

            // Retrieve the generated key (if insertion was successful)
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = prepstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1); // the generated ID
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return generatedId; // Return the generated ID
    }

    /**
     * Gets a pet from the Pets table by its ID.
     *
     * @param petId the ID of the pet to retrieve
     * @return a Pets object representing the pet, or null if not found
     */
    public static Pets getPet(int petId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT id, name, birthDay, gender, status, castration, shortDescription, longDescription,type, size, isAdopted, adoptionDate,image, imageType, owner_id FROM Pets WHERE id = ?")) {
            // query to select a pet by its ID
            prepstmt.setInt(1, petId);
            ResultSet rs = prepstmt.executeQuery(); // extract the result set
            if (rs.next()) {
                // return the pet object
                return new Pets(petId, rs.getString("name"), rs.getDate("birthDay"), rs.getString(
                        "gender"), rs.getBoolean("status"), rs.getBoolean("castration"), rs.getString(
                        "shortDescription"), rs.getString("longDescription"), rs.getString("type"),
                        rs.getString("size"), rs.getBoolean("isAdopted"), rs.getDate("adoptionDate"), rs.getInt("owner_id"), rs.getString("image"), rs.getString("imageType"));


            } else {
                System.out.println("pet have not been found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // return null if the pet is not found
        return null;
    }

    /**
     * Gets a list of pets from the Pets table based on the provided filter criteria.
     *
     * @param filter the filter criteria as a map of key-value pairs
     * @return the list of pets that match the filter criteria
     */
    public static List<Pets> getFilteredPets(Map<String, Object> filter) {
        List<Pets> pets = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Pets p JOIN USERS u on p.owner_id=u.id JOIN Cities c ON u.cityID = c.id WHERE 1=1"); // base SQL query
        List<Object> paramValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) { // switch case to handle different filter criteria
                case "ageLessThan": { // check if the key is "ageLessThan"
                    int age = Integer.parseInt((String) value);
                    LocalDate localDate = LocalDate.now().minusYears(age);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                    sql.append(" AND birthDay >= ?"); // add condition to the SQL query for age less than
                    paramValues.add(sqlDate);
                    break;
                }
                case "ageMoreThan": { // check if the key is "ageMoreThan"
                    int age = Integer.parseInt((String) value);
                    LocalDate localDate = LocalDate.now().minusYears(age);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                    sql.append(" AND birthDay <= ?"); // add condition to the SQL query for age more than
                    paramValues.add(sqlDate);
                    break;
                }
                case "district": {
                    sql.append(" AND c.district = ?");
                    paramValues.add(value);
                    break;
                }
                default: { // default case for other filter criteria
                    sql.append(" AND ").append(key).append(" = ?");
                    paramValues.add(value);
                    break;
                }
            }

        }
        try (Connection conn = DriverManager.getConnection(DB_URL); // builds back the criteria and gets the selected pets
             PreparedStatement prepstmt = conn.prepareStatement(sql.toString())) { // the statement query and sets the prepared statement for extructing the result set
            for (int i = 0; i < paramValues.size(); i++) { // loop through the parameters and set them in the prepared statement
                prepstmt.setObject(i + 1, paramValues.get(i));
            }
            ResultSet rs = prepstmt.executeQuery();
            while (rs.next()) { // get all the pets that match the criteria
                pets.add(new Pets(rs.getInt("id"), rs.getString("name"), rs.getDate("birthDay"),
                        rs.getString("gender"), rs.getBoolean("status"), rs.getBoolean("castration"), rs.getString(
                        "shortDescription"), rs.getString("longDescription"), rs.getString("type"),
                        rs.getString("size"), rs.getBoolean("isAdopted"), rs.getDate("adoptionDate"), rs.getInt("owner_id"), rs.getString("image"), rs.getString("imageType")));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pets;
    }

    /**
     * Updates a pet in the Pets table.
     *
     * @param petId            the ID of the pet to update
     * @param name             the new name of the pet
     * @param birthDay         the new birthday of the pet
     * @param gender           gender of the pet
     * @param status           status of the pet
     * @param castration       castration status of the pet
     * @param shortDescription short description of the pet
     * @param longDescription  long description of the pet
     * @param type             type of the pet
     * @param size             size of the pet
     * @param isAdopted        adoption status of the pet
     * @param adoptionDate     adoption date of the pet
     * @param image            image in base 64 of the pet
     * @param imageType        image type of the pet
     * @param owner_id         owner ID of the pet
     */
    public static void updatePet(int petId, String name, Date birthDay, String gender,
                                 boolean status, boolean castration, String shortDescription, String longDescription,
                                 String type, String size, boolean isAdopted, Date adoptionDate, String image, String imageType, int owner_id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("UPDATE Pets SET name = ?, birthDay=?, " +
                     "gender=?, status=?, castration=?, shortDescription=?, longDescription=?, type = ?, size = ?,isAdopted =?, adoptionDate=? , image=?, imageType=?" +
                     "owner_id=? WHERE id = ?")) { // query to update a pet
            prepstmt.setString(1, name);
            prepstmt.setDate(2, birthDay);
            prepstmt.setString(3, gender);
            prepstmt.setBoolean(4, status);
            prepstmt.setBoolean(5, castration);
            prepstmt.setString(6, shortDescription);
            prepstmt.setString(7, longDescription);
            prepstmt.setString(8, type);
            prepstmt.setString(9, size);
            prepstmt.setBoolean(10, isAdopted);
            prepstmt.setDate(11, adoptionDate);
            prepstmt.setString(10, image);
            prepstmt.setString(11, imageType);
            prepstmt.setInt(12, owner_id);
            prepstmt.setInt(13, petId);

            int executionStatus = prepstmt.executeUpdate();
            if (executionStatus > 0) { // check if the update was successful
                System.out.println("Pet updated successfully");
            } else { // check if the pet was found
                System.out.println("Pet not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates a pet in the Pets table by a pet.
     *
     * @param id  the ID of the pet to update
     * @param pet the Pets object containing the new values
     */
    public static void updatePet(int id, Pets pet) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("UPDATE Pets SET name = ?, birthDay=?, " +
                     "gender=?, status=?, castration=?, shortDescription=?, longDescription=?, type = ?, size = ?,isAdopted =?, adoptionDate=? ,owner_id=? WHERE id = ?")) { // query to update a pet
            prepstmt.setString(1, pet.getName());
            prepstmt.setDate(2, pet.getBirthDay());
            prepstmt.setString(3, pet.getGender());
            prepstmt.setBoolean(4, pet.getStatus());
            prepstmt.setBoolean(5, pet.getCastration());
            prepstmt.setString(6, pet.getShortDescription());
            prepstmt.setString(7, pet.getLongDescription());
            prepstmt.setString(8, pet.getType());
            prepstmt.setString(9, pet.getSize());
            prepstmt.setBoolean(10, pet.getIsAdopted());
            prepstmt.setDate(11, pet.getAdoptionDate());
            prepstmt.setInt(12, pet.getPetsOwnerId());
            prepstmt.setInt(13, id);

            int executionStatus = prepstmt.executeUpdate(); // execute the update
            if (executionStatus > 0) {
                System.out.println("Pet updated successfully");
            } else {
                System.out.println("Pet not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the photo of a pet in the Pets table.
     *
     * @param id  the ID of the pet to update
     * @param pet the Pets object containing the new image and image type
     */
    public static void changePhoto(int id, Pets pet) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("UPDATE Pets SET  image=? , imageType=? WHERE id = ?")) { // query to update the photo of a pet
            prepstmt.setString(1, pet.getImage());
            prepstmt.setString(2, pet.getImageType()); // set the new image and image type
            prepstmt.setInt(3, id);

            int executionStatus = prepstmt.executeUpdate(); // execute the update
            if (executionStatus > 0) {
                System.out.println("Pet updated successfully");
            } else {
                System.out.println("Pet not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a pet from the Pets table by its ID.
     *
     * @param petId the ID of the pet to delete
     */
    public static void deletePet(int petId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("UPDATE Pets SET  status=? WHERE id = ?")) { // query to delete a pet by its ID
            prepstmt.setInt(1, 0);
            prepstmt.setInt(2, petId);
            int executionStatus = prepstmt.executeUpdate();
            if (executionStatus > 0) {
                System.out.println("Pet deleted successfully.");
            } else {
                System.out.println("No pet found with ID: " + petId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting pet: " + e.getMessage());
        }
    }
}
