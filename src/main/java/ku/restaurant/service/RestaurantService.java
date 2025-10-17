package ku.restaurant.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantService {
    private RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }
    
    public List<Restaurant> getAll() {
        return repository.findAll();
    }
    
    public Restaurant getRestaurantById(UUID id) {
        return repository.findById(id).get();
    }
    
    public Restaurant getRestaurantByName(String name) {
       return repository.findByName(name);
    }
 
 
    public List<Restaurant> getRestaurantByLocation(String location) {
       return repository.findByLocation(location);
    }


    public Restaurant create(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setRating(request.getRating());
        restaurant.setLocation(request.getLocation());
        restaurant.setCreatedAt( Instant.now() );
        Restaurant record = repository.save(restaurant);
        return record; 
    }
    
    public Restaurant update(Restaurant requestBody) {
       UUID id = requestBody.getId();
       Restaurant record = repository.findById(id).get();
       record.setName(requestBody.getName());
       record.setRating(requestBody.getRating());
       record.setLocation(requestBody.getLocation());    
       record = repository.save(record);
       return record;
    }
    
    public Restaurant delete(UUID id) {
       Restaurant record = repository.findById(id).get();
       repository.deleteById(id);
       return record;
    }


}
