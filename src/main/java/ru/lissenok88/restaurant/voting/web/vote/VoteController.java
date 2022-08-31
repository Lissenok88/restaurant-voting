package ru.lissenok88.restaurant.voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.error.IllegalRequestDataException;
import ru.lissenok88.restaurant.voting.model.User;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;

import static ru.lissenok88.restaurant.voting.util.TimeUtil.isBeforeTimeLimit;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile/votes";

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant {}", restaurantId);
        User user = authUser.getUser();
        Vote created = voteRepository.save(new Vote(restaurantRepository.getById(restaurantId), user, LocalDate.now()));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        Vote vote = voteRepository.getByUserByDate(LocalDate.now(), user).orElseThrow(
                () -> new IllegalRequestDataException("You haven't voted today")
        );

        log.info("update vote {}", vote);
        if (isBeforeTimeLimit()) {
            vote.setRestaurant(restaurantRepository.getById(restaurantId));
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("You cannot vote again today");
        }
    }
}