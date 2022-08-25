package ru.lissenok88.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lissenok88.restaurant.voting.util.RestaurantUtil;
import ru.lissenok88.restaurant.voting.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantController.REST_URL;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;
import static ru.lissenok88.restaurant.voting.web.user.UserTestData.*;
import static ru.lissenok88.restaurant.voting.web.vote.VoteTestData.vote1;
import static ru.lissenok88.restaurant.voting.web.vote.VoteTestData.vote3;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menus"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENUS_MATCHER.contentJson(
                        RestaurantUtil.getTos(List.of(restaurant_1, restaurant_2, restaurant_3), List.of(vote1, vote3))));
    }
}