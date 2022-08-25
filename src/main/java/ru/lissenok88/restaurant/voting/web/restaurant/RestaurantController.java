package ru.lissenok88.restaurant.voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.to.RestaurantTo;
import ru.lissenok88.restaurant.voting.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {

    static final String REST_URL = "/api/profile/restaurants";

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping(value = "/with-menus")
    public List<RestaurantTo> getAllWithMenuToday() {
        log.info("get all restaurants with menu today");
        return RestaurantUtil.getTos(restaurantRepository.getAllWithMenusByDate(LocalDate.now()), voteRepository.findAll());
    }
}