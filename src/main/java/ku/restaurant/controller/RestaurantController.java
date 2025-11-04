package ku.restaurant.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<Restaurant>> getAllRestaurants(
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = 10;
        if (StringUtils.isEmpty(sortBy)) sortBy = "name";

        return ResponseEntity.ok(
            service.getRestaurantPage(
                PageRequest.of(offset, pageSize, Sort.by(sortBy))
            )
        );
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> create(
        @Valid @RequestBody RestaurantRequest restaurant
    ) {
        return ResponseEntity.ok(service.create(restaurant));
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getRestaurantById(id));
    }

    @PutMapping("/restaurants")
    public ResponseEntity<Restaurant> update(
        @RequestBody Restaurant restaurant
    ) {
        return ResponseEntity.ok(service.update(restaurant));
    }

    @DeleteExchange("/restaurants/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurants/name/{name}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getRestaurantByName(name));
    }

    @GetMapping("/restaurants/location/{location}")
    public ResponseEntity<List<Restaurant>> getRestaurantByLocation(
        @PathVariable String location
    ) {
        return ResponseEntity.ok(service.getRestaurantByLocation(location));
    }
}
