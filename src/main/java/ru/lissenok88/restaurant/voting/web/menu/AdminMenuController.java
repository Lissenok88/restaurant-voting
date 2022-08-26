package ru.lissenok88.restaurant.voting.web.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.model.Menu;
import ru.lissenok88.restaurant.voting.repository.MenuRepository;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lissenok88.restaurant.voting.util.validation.ValidationUtil.checkNew;
import static ru.lissenok88.restaurant.voting.web.restaurant.AdminRestaurantController.REST_URL;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL_MENU, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AdminMenuController {
    static final String REST_URL_MENU = REST_URL + "/{restaurantId}/menus";

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Menu> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(menuRepository.get(id, restaurantId));
    }

    @GetMapping()
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("get all menus for restaurant {}", restaurantId);
        return menuRepository.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create menu {} for restaurant {}", menu, restaurantId);
        checkNew(menu);
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        Menu created = menuRepository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update menu {} for restaurant {} ", menu, restaurantId);
        ValidationUtil.assureIdConsistent(menu, id);
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        menuRepository.save(menu);
    }
}