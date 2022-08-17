package ru.lissenok88.restaurant.voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.model.Menu;
import ru.lissenok88.restaurant.voting.repository.MenuRepository;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.util.validation.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menu";

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public AdminMenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create menu {} for restaurant {}", menu, restaurantId);
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        Menu created = menuRepository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update menu {} for restaurant {} ", menu, restaurantId);
        ValidationUtil.assureIdConsistent(menu, id);
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        menuRepository.save(menu);
    }

    @GetMapping
    public List<Menu> getAll() {
        log.info("get all menu");
        return menuRepository.findAll();
    }
}