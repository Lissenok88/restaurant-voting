package ru.lissenok88.restaurant.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.repository.VoteRepository;
import ru.lissenok88.restaurant.voting.util.TimeUtil;
import ru.lissenok88.restaurant.voting.util.JsonUtil;
import ru.lissenok88.restaurant.voting.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lissenok88.restaurant.voting.web.menu.MenuTestData.*;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;
import static ru.lissenok88.restaurant.voting.web.user.UserTestData.*;
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
    void updateBeforeTimeLimit() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        TimeUtil.setFixedTime(LocalTime.of(10, 30));
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                .param("restaurantId", String.valueOf(RESTAURANT_2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(voteRepository.getById(userVote.id()), VoteTestData.getUpdated());
        TimeUtil.setDefaultTime();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterTimeLimit() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        TimeUtil.setFixedTime(LocalTime.of(15, 30));
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                .param("restaurantId", String.valueOf(RESTAURANT_2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        TimeUtil.setDefaultTime();
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = USER_MAIL)
    void createWithLocationDuplicate() {
        Vote newVote = new Vote(null, userVote.getRestaurant(), CURRENT_DATE);
        assertThrows(Exception.class, () ->
                perform(MockMvcRequestBuilders.post(REST_URL)
                        .param("restaurantId", String.valueOf(RESTAURANT_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newVote)))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity()));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(userVote));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}