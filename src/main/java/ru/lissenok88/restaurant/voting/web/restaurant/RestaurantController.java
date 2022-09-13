package ru.lissenok88.restaurant.voting.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "restaurantsWithMenuItems")
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    @GetMapping(value = "/with-menu-items")
    @Cacheable
    public List<Restaurant> getAllWithMenuToday() {
        log.info("get all restaurants with menu items today");
        return restaurantRepository.getAllWithMenuItemsByDate(LocalDate.now());
    }
}