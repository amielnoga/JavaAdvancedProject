package BackEnd.utils;

import BackEnd.DBHelper.CitiesDBHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for importing cities data from the Israeli government open data API and storing it into the
 * application's database.
 */
public class CitiesUtil {

    /**
     * Fetches city records from the Israeli government open data API and inserts the into the application's database.
     * The method sends a GET request to the API, parses the JSON response using Jackson and processes each city record.
     */
    public static void importCitiesFromGovAPI() {
        String apiURL = "https://data.gov.il/api/3/action/datastore_search?resource_id=1bf27e56-364c-4b61-8b6b" +
                "-efa9933da677";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(connection.getInputStream());
            JsonNode records = root.path("result").path("records");

            for (JsonNode record : records) {
                String name = record.path("ENGLISH_NAME").asText();
                String district = record.path("MACHOZ").asText();
                if (!name.isEmpty() && !district.isEmpty()) {
                    CitiesDBHelper.addCity(name, convertToDistrictName(district));
                }
            }
            System.out.println("Cities imported successfully from Gov API.");
        } catch (IOException e) {
            System.err.println("Error fetching cities: " + e.getMessage());
        }
    }

    /**
     * Converts a district (as returned by the API) into a district name.
     *
     * @param district The district identifier
     * @return The corresponding  name of the district
     */
    public static String convertToDistrictName(String district) {
        switch (district) {
            case "גולן גליל":
                return "North";
            case "מרכז":
                return "Center";
            case "השפלה וההר":
                return "Jerusalem";
            case "נגב":
                return "South";
            case "עמקים":
                return "Valleys";
            default:
                return "Unknown";
        }
    }
}
