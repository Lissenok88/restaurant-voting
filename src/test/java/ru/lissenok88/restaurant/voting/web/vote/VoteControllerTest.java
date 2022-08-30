package ru.lissenok88.restaurant.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.util.JsonUtil;
import ru.lissenok88.restaurant.voting.web.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;
import static ru.lissenok88.restaurant.voting.web.user.UserTestData.*;
import static ru.lissenok88.restaurant.voting.web.vote.VoteController.TIME_LIMIT;
import static ru.lissenok88.restaurant.voting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = BOB_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        if (LocalDateTime.now().toLocalTime().compareTo(TIME_LIMIT) < 0) {
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", String.valueOf(RESTAURANT_2))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(voteRepository.getById(vote1.id()), VoteTestData.getUpdated());
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", String.valueOf(RESTAURANT_2))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }
}