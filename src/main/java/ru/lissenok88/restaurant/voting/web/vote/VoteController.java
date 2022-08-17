package ru.lissenok88.restaurant.voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.model.User;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.util.validation.ValidationUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    static final String REST_URL = "/rest/restaurants/{restaurantId}/vote";

    private final LocalTime TIME_LIMIT = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@PathVariable Restaurant restaurantId, @PathVariable User user) {
        log.info("create vote for restaurant {}", restaurantId);
        Vote created = voteRepository.save(new Vote(restaurantId, user, LocalDate.now()));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Restaurant restaurantId, @PathVariable User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Vote vote = voteRepository.getByUserByDate(localDateTime.toLocalDate(), user);
        ValidationUtil.assureIdConsistent(vote.getRestaurant(), restaurantId.id());
        log.info("update vote {}", vote);
        if (localDateTime.toLocalTime().compareTo(TIME_LIMIT) < 0) {
            voteRepository.save(new Vote(vote.id(), restaurantId, user, localDateTime.toLocalDate()));
        }
    }
}