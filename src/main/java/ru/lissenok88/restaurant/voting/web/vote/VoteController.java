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
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<Vote> getByUserToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get votes today");
        return ResponseEntity.of(voteRepository.getByDate(LocalDate.now(), authUser.id()));
    }

    @GetMapping("/history-votes")
    public List<Vote> getByUserHistoryVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get history votes");
        return voteRepository.getByUser(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant {}", restaurantId);
        int userId = authUser.id();
        if (voteRepository.getByDate(LocalDate.now(), userId).isPresent())
            throw new IllegalRequestDataException("User " + userId + " already voted today " +
                    "so the re-vote should be performed instead of new vote creation");

        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Vote vote = new Vote(restaurant, LocalDate.now());
        vote.setUser(userRepository.getById(userId));
        Vote created = voteRepository.save(vote);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        Vote vote = voteRepository.getByDate(LocalDate.now(), userId).orElseThrow(
                () -> new IllegalRequestDataException("You haven't voted today")
        );

        log.info("update vote {}", vote);
        if (isBeforeTimeLimit()) {
            Restaurant restaurant = restaurantRepository.getById(restaurantId);
            assureIdConsistent(restaurant, restaurantId);
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("User " + userId + " already voted today" +
                    " so due it's already after past 11am, the re-vote cannot be performed");
        }
    }
}