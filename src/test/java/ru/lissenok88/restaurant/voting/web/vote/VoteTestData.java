package ru.lissenok88.restaurant.voting.web.vote;

import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.web.MatcherFactory;

import java.time.LocalDate;

import static ru.lissenok88.restaurant.voting.web.menu.MenuTestData.CURRENT_DATE;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final int VOTE_ID = 1;
    public static final int VOTE_ID_NOT_FOUND = 30;

    public static final Vote userVote = new Vote(VOTE_ID, restaurant_1, CURRENT_DATE);
    public static final Vote adminVote = new Vote(VOTE_ID + 1, restaurant_3, CURRENT_DATE);

    public static Vote getNew() {
        return new Vote(null, restaurant_2, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, restaurant_2, CURRENT_DATE);
    }
}