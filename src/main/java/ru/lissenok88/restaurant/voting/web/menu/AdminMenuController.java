package ru.lissenok88.restaurant.voting.web.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.model.Menu;
import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.repository.MenuRepository;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.web.restaurant.AdminRestaurantController;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.lissenok88.restaurant.voting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.lissenok88.restaurant.voting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "restaurantsWithMenuItems")
public class AdminMenuController {
    static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/menu-items";

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Menu> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(menuRepository.get(id, restaurantId));
    }

    @GetMapping()
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("get all menu items for restaurant {}", restaurantId);
        return menuRepository.getAll(restaurantId);
    }

    @GetMapping("/by-date")
    public List<Menu> getAllByDate(@PathVariable int restaurantId,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        log.info("get all menu items for restaurant {} by date {}", restaurantId, localDate);
        return menuRepository.getAllByDate(restaurantId, localDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create menu {} for restaurant {}", menu, restaurantId);
        checkNew(menu);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update menu {} for restaurant {} ", menu, restaurantId);
        assureIdConsistent(menu, id);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        Menu menu = menuRepository.checkBelong(id, restaurantId);
        menuRepository.delete(menu);
    }
}