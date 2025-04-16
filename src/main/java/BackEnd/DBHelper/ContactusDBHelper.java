package BackEnd.DBHelper;

import BackEnd.models.ContactUs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactusDBHelper {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/DataBases.db";

    /**
     * Creates a new SQLite and creates the Contactus table if it does not exist.
     * The table has eight columns: id, message, dateRequest, status, email, firstName, lastName, phoneNumber,
     * and requestType.
     */
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) { // Create a connection to the database
            String sql = "CREATE TABLE IF NOT EXISTS Contactus (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " message TEXT NOT NULL,\n"
                    + " dateRequest DATE NOT NULL,\n"
                    + " status TEXT NOT NULL,\n"
                    + "email TEXT NOT NULL,\n"
                    + "firstName TEXT NOT NULL,\n"
                    + "lastName TEXT NOT NULL,\n"
                    + "phoneNumber TEXT NOT NULL,\n"
                    + "requestType TEXT NOT NULL\n"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new contact request to the Contactus table.
     *
     * @param firstName   The first name of the person making the request.
     * @param lastName    The last name of the person making the request.
     * @param phoneNum    The phone number of the person making the request.
     * @param email       The email address of the person making the request.
     * @param message     The message content of the request.
     * @param date        The date of the request.
     * @param status      The status of the request.
     * @param requestType The type of request.
     */
    public static void addRequest(String firstName, String lastName, String phoneNum, String email, String message,
                                  Date date, String status, String requestType) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Contactus (message, dateRequest , " +
                     "status,email,firstName,lastName,phoneNumber,requestType) VALUES (?, ?, ?, ?,?,?,?,?)")) { // query to insert a new request
            prepstmt.setString(1, message);
            prepstmt.setDate(2, date);
            prepstmt.setString(3, status);
            prepstmt.setString(4, email);
            prepstmt.setString(5, firstName);
            prepstmt.setString(6, lastName);
            prepstmt.setString(7, phoneNum);
            prepstmt.setString(8, requestType);
            prepstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get a contact request by its ID.
     *
     * @param requestId The ID of the request to retrieve.
     * @return A ContactUs object representing the request, or null if not found.
     */
    public static ContactUs getRequest(int requestId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT id, message, dateRequest, status,requestType " +
                     "FROM Contactus WHERE id = ?")) { // query to select a request by ID
            prepstmt.setInt(1, requestId);
            ResultSet rs = prepstmt.executeQuery();
            if (rs.next()) {
                return new ContactUs(rs.getInt("id"),
                        rs.getString("message"),
                        rs.getDate("dateRequest"),
                        rs.getString("status"),
                        rs.getString("email"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("requestType"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all contact requests from the Contactus table.
     *
     * @return A list of ContactUs objects representing all requests.
     */
    public static List<ContactUs> getAllRequests() {
        List<ContactUs> requests = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT id, message, dateRequest, status, email, " +
                     "firstName, " +
                     "lastName, phoneNumber,requestType FROM Contactus"); // query to select all requests
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContactUs contact = new ContactUs(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getDate("dateRequest"),
                        rs.getString("status"),
                        rs.getString("email"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("requestType")
                );
                requests.add(contact);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return requests;
    }

    /**
     * Updates the status of a contact request in the Contactus table.
     *
     * @param id        The ID of the request to update.
     * @param newStatus The new status to set for the request.
     */
    public static void updateRequestStatus(int id, String newStatus) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("UPDATE Contactus SET status = ? WHERE id = ?")) { // query to update the status
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

