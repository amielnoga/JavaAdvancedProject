package BackEnd.DBHelper;

import BackEnd.models.Cities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitiesDBHelper {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/DataBases.db";

    /**
     * Creates a new SQLite database connection and creates the Cities table if it does not exist.
     * The table has three columns: id, name, and district.
     */
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) { // Create a connection to the database
            stmt.execute("DROP TABLE IF EXISTS Cities");
            String sql = "CREATE TABLE IF NOT EXISTS Cities (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " name TEXT NOT NULL,\n"
                    + " district TEXT NOT NULL\n"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new city to the Cities table.
     *
     * @param name     The name of the city.
     * @param district The district of the city.
     */
    public static void addCity(String name, String district) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Cities (name, district) VALUES (?, ?)")) {
            prepstmt.setString(1, name);
            prepstmt.setString(2, district);
            prepstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves a city from the Cities table by its name.
     * @param name The name of the city to retrieve.
     * @return A Cities object representing the city, or null if not found.
     */
    public static Cities getCity(String name){
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM Cities WHERE name = ?")) { // query to select a city by name
            prepstmt.setString(1, name);
            ResultSet rs = prepstmt.executeQuery();
            if (rs.next()) {
                return new Cities(rs.getInt("id"), rs.getString("name"), rs.getString("district"));
            } else {
                System.out.println("City not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a city from the Cities table by its ID.
     * @param cityId The ID of the city to retrieve.
     * @return A Cities object representing the city, or null if not found.
     */
    public static Cities getCity(int cityId){
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM Cities WHERE id = ?")) { // Query to select a city by its ID
            prepstmt.setInt(1, cityId);
            ResultSet rs = prepstmt.executeQuery();
            if (rs.next()) {
                return new Cities(rs.getInt("id"), rs.getString("name"), rs.getString("district"));
            } else {
                System.out.println("City not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the ID of a city from the Cities table by its name.
     * @param name The name of the city to retrieve the ID for.
     * @return The ID of the city, or -1 if not found.
     */
    public static int getCityId(String name){
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM Cities WHERE name = ?")) {
            prepstmt.setString(1, name);
            ResultSet rs = prepstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.out.println("City not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves all cities from the Cities table.
     * @return A list of Cities objects representing all cities in the table.
     */
    public static List<Cities> getAllCities() {
        List<Cities> cities = new ArrayList<>();
        String sql = "SELECT id, name,district FROM cities ORDER BY name"; // SQL query to select all cities

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { // Iterate through the result set and adds each city to the list
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String district = rs.getString("district");
                cities.add(new Cities(id, name, district));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }
}