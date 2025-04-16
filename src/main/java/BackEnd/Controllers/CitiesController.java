package BackEnd.Controllers;

import BackEnd.Services.CitiesService;
import BackEnd.models.Cities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CitiesController {

    // The CitiesService instance used to interact with the cities' database.
    @Autowired
    private CitiesService citiesService;

    /**
     * Endpoint to retrieve all cities.
     *
     * @return A list of all cities.
     */
    @PutMapping("/all")
    public List<Cities> getAllCities() {
        return citiesService.getAllCities();
    }

    /**
     * Endpoint to retrieve a city by its ID.
     *
     * @param id the ID of the city to retrieve.
     * @return the city with the specified ID.
     */
    @GetMapping("/{id}")
    public Cities getCityById(@PathVariable int id) {
        return citiesService.getCityById(id);
    }
}
