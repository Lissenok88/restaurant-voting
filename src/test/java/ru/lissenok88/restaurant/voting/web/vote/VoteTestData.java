package ru.lissenok88.restaurant.voting.web.vote;

import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.web.MatcherFactory;

import java.time.LocalDate;

import static ru.lissenok88.restaurant.voting.web.menu.MenuTestData.CURRENT_DATE;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;
import static ru.lissenok88.restaurant.voting.web.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "users");

    public static final Vote vote1 = new Vote(restaurant_1, user, CURRENT_DATE);
    public static final Vote vote3 = new Vote(restaurant_3, admin, CURRENT_DATE);

    public static Vote getNew() {
        return new Vote(null, restaurant_1, user, LocalDate.now());
    }

    public static Vote getUpdated() {
        Vote updatedVote = vote1;
        updatedVote.setRestaurant(restaurant_2);
        return updatedVote;
    }
}
