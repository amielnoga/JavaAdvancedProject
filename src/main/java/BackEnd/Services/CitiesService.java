package BackEnd.Services;

import BackEnd.DBHelper.CitiesDBHelper;
import BackEnd.models.Cities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesService {
    /**
     * Retrieves all cities from the database.
     *
     * @return A list of all cities.
     */
    public List<Cities> getAllCities() {
        return CitiesDBHelper.getAllCities();
    }

    /**
     * Retrieves a city by its ID.
     *
     * @param id The ID of the city to retrieve.
     * @return The city with the specified ID.
     */
    public Cities getCityById(int id) {
        return CitiesDBHelper.getCity(id);
    }
}
