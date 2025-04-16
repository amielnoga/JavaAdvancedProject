package BackEnd.DBHelper;

import BackEnd.models.Users;

import java.sql.*;

public class UserDBHelper {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/DataBases.db";

    /**
     * Creates the Users table in the database if it does not exist.
     * with the data types and constraints specified in the SQL statement.
     */
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " userName TEXT UNIQUE,\n"
                    + " password TEXT NOT NULL,\n"
                    + " firstName TEXT NOT NULL,\n"
                    + " lastName TEXT NOT NULL,\n"
                    + " phoneNum TEXT NOT NULL,\n"
                    + " cityId INT NOT NULL,\n"
                    + " adminPerms BOOL NOT NULL,\n"
                    + " email TEXT NOT NULL,\n"
                    + "    FOREIGN KEY (cityId) REFERENCES Cities(id)\n"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new user to the Users table in the database.
     *
     * @param userName   the username of the user
     * @param password   the password of the user
     * @param firstName  the first name of the user
     * @param lastName   the last name of the user
     * @param phoneNum   the phone number of the user
     * @param cityId     the city ID of the user
     * @param adminPerms the admin permissions of the user
     * @param email      the email of the user
     */
    public static void addUser(String userName, String password, String firstName, String lastName, String phoneNum, int cityId, boolean adminPerms, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Users (userName, password, firstName, lastName, phoneNum, cityId, adminPerms, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) { // the SQL query to insert a new user
            prepstmt.setString(1, userName);
            prepstmt.setString(2, password);
            prepstmt.setString(3, firstName);
            prepstmt.setString(4, lastName);
            prepstmt.setString(5, phoneNum);
            prepstmt.setInt(6, cityId);
            prepstmt.setBoolean(7, adminPerms);
            prepstmt.setString(8, email);
            prepstmt.executeUpdate(); // execute the SQL query to insert the new user
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new user to the Users table in the database by passing a Users object.
     *
     * @param user the user object to add
     */
    public static void addUser(Users user) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO Users (userName, password, firstName, lastName, phoneNum, cityId, adminPerms, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) { // the SQL query to insert a new user
            prepstmt.setString(1, user.getUserName());
            prepstmt.setString(2, user.getUserPassword());
            prepstmt.setString(3, user.getUserFirstName());
            prepstmt.setString(4, user.getUserLastName());
            prepstmt.setString(5, user.getUserPhoneNum());
            prepstmt.setInt(6, user.getUserCityId());
            prepstmt.setBoolean(7, user.getUserAdminPerms());
            prepstmt.setString(8, user.getUserEmail());
            prepstmt.executeUpdate(); // execute the SQL query to insert the new user

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets a user from the Users table in the database by user ID.
     *
     * @param userId the ID of the user to Gets
     * @return the user object with the specified ID
     */
    public static Users getUser(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("SELECT id, userName, password, firstName, lastName, phoneNum, cityId, adminPerms, email FROM Users WHERE id = ?")) { // the SQL query to get a user by ID

            prepstmt.setInt(1, userId);

            ResultSet rs = prepstmt.executeQuery(); // execute the SQL query to get the user
            if (rs.next()) { // sets the values of the user object to the values from the database if successful
                return new Users(rs.getString("userName"), rs.getString("password"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNum"), rs.getInt("cityId"), rs.getBoolean("adminPerms"), rs.getString("email"), rs.getInt("id"));
            } else {
                System.out.println("User have not been found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // return null if the user is not found
    }

    /**
     * Updates a user in the Users table in the database by passing the user details.
     *
     * @param password  the password of the user to change
     * @param firstName the first name of the user to change
     * @param lastName  the last name of the user to change
     * @param phoneNum  the phone number of the user to change
     * @param cityId    the city ID of the user to change
     * @param email     the email of the user to change
     * @param userName  the username of the user
     */
    public static void updateUser(String password, String firstName, String lastName, String phoneNum, int cityId,
                                  String email, String userName) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement("UPDATE Users SET password=?, firstName=?, " +
                     "lastName=?, phoneNum=?, cityId=?, email=? WHERE userName = ?")) { // the SQL query to update a user
            prepstmt.setString(1, password);
            prepstmt.setString(2, firstName);
            prepstmt.setString(3, lastName);
            prepstmt.setString(4, phoneNum);
            prepstmt.setInt(5, cityId);
            prepstmt.setString(6, email);
            prepstmt.setString(7, userName);

            int executionStatus = prepstmt.executeUpdate(); // execute the SQL query to update the user
            if (executionStatus > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates a user in the Users table in the database by passing a Users object.
     *
     * @param user the updated user object
     */
    public static void updateUser(Users user) {
        if (user.getUserPassword().equals("null")) { // if the user password is null, update the other fields
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement prepstmt = conn.prepareStatement("UPDATE Users SET firstName=?, " +
                         "lastName=?, phoneNum=?, cityId=?, email=? WHERE userName = ?")) { // the SQL query to update a user

                prepstmt.setString(1, user.getUserFirstName());
                prepstmt.setString(2, user.getUserLastName());
                prepstmt.setString(3, user.getUserPhoneNum());
                prepstmt.setInt(4, user.getUserCityId());
                prepstmt.setString(5, user.getUserEmail());
                prepstmt.setString(6, user.getUserName());

                int executionStatus = prepstmt.executeUpdate(); // the SQL query to update the user
                if (executionStatus > 0) {
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("User not found");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else { // if the user password is not null, update the password
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement prepstmt = conn.prepareStatement("UPDATE Users SET password=? WHERE userName = ?")) {
                // update the password of the user
                prepstmt.setString(1, user.getUserPassword());
                prepstmt.setString(2, user.getUserName());

                int executionStatus = prepstmt.executeUpdate();
                if (executionStatus > 0) {
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("User not found");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Gets a user from the Users table in the database by username.
     *
     * @param userName the username of the user to get
     * @return the user object with the specified username
     */
    public static Users getUserByUsername(String userName) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepstmt = conn.prepareStatement(
                     "SELECT id, userName, password, firstName, lastName, phoneNum, cityId, adminPerms, email " +
                             "FROM Users WHERE userName = ?")) { // the SQL query to get a user by username

            prepstmt.setString(1, userName);

            ResultSet rs = prepstmt.executeQuery(); // execute the SQL query to get the user
            if (rs.next()) { // create a new user object with the values from the database
                return new Users(
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNum"),
                        rs.getInt("cityId"),
                        rs.getBoolean("adminPerms"),
                        rs.getString("email"),
                        rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // return null if the user is not found
    }

}
