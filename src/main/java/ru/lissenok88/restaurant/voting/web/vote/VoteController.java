package ru.lissenok88.restaurant.voting.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lissenok88.restaurant.voting.error.IllegalRequestDataException;
import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.repository.RestaurantRepository;
import ru.lissenok88.restaurant.voting.repository.UserRepository;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;

import static ru.lissenok88.restaurant.voting.util.TimeUtil.isBeforeTimeLimit;
import static ru.lissenok88.restaurant.voting.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile/vote";

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {}", id);
        return ResponseEntity.of(voteRepository.get(id, authUser.id()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant {}", restaurantId);
        int userId = authUser.id();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        Vote vote = new Vote(restaurant, LocalDate.now());
        vote.setUser(userRepository.getById(userId));
        Vote created = voteRepository.save(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        Vote vote = voteRepository.getByDate(LocalDate.now(), userId).orElseThrow(
                () -> new IllegalRequestDataException("You haven't voted today")
        );
        assureIdConsistent(vote, id);

        log.info("update vote {}", vote);
        if (isBeforeTimeLimit()) {
            Restaurant restaurant = restaurantRepository.getById(restaurantId);
            assureIdConsistent(restaurant, restaurantId);
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("You cannot vote again today");
        }
    }
}