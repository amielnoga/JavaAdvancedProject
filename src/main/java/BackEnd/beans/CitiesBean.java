package BackEnd.beans;

import BackEnd.models.Cities;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * This class provides access to the list of cities. This bean is initialized once at application startup and holds the
 * list of cities retrieved from the database.
 */
@Component("CitiesBean")
@ApplicationScope
public class CitiesBean {
    private final static String URL = "http://localhost:8080";
    private List<Cities> cities;

    /**
     * This method is called automatically, and it populates the cities list by fetching them from the database.
     */
    @PostConstruct
    public void init() {
        String pathUri = URL + "/cities/all";
        try{
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Void> requestEntity = (HttpEntity<Void>) HttpEntity.EMPTY;
        ResponseEntity<List<Cities>> response = restTemplate.exchange(pathUri, HttpMethod.PUT, requestEntity,
                new ParameterizedTypeReference<List<Cities>>() {
                });
        cities = response.getBody();
        }
        catch (Exception e) {
            System.out.println("Error fetching cities: " + e.getMessage());
            System.out.println("please check the db connection from ministery of interior");
        }
    }

    /**
     * Returns a city by its ID.
     *
     * @param id the ID of the city to retrieve.
     * @return the city with the specified ID.
     */
    public static Cities getCityById(int id) {
        String pathUri = URL + "/cities/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Cities> response = restTemplate.getForEntity(pathUri, Cities.class);
        return response.getBody();
    }

    /**
     * Returns the list of cities.
     *
     * @return a list of all the cities.
     */
    public List<Cities> getCities() {
        return cities;
    }
}

