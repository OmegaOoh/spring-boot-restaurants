package ku.restaurant.controller;

import java.util.List;
import java.util.UUID;

import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {
        return service.getAll();
    }

    @PostMapping("/restaurants")
    public Restaurant create(@RequestBody RestaurantRequest restaurant) {
        return service.create(restaurant);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurantById(@PathVariable UUID id) {
        return service.getRestaurantById(id);
    }
    
    @PutMapping("/restaurants")
    public Restaurant update(@RequestBody Restaurant restaurant) {
       return service.update(restaurant);
    }
    
    @DeleteExchange("/restaurants/{id}")
    public Restaurant delete(@PathVariable UUID id) {
       return service.delete(id);
    }
    
    @GetMapping("/restaurants/name/{name}")
    public Restaurant getRestaurantByName(@PathVariable String name) {
        return service.getRestaurantByName(name);
    }
 
 
    @GetMapping("/restaurants/location/{location}")
    public List<Restaurant> getRestaurantByLocation(@PathVariable String location) {
        return service.getRestaurantByLocation(location);
    }
    
}
