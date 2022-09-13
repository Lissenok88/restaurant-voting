package ru.lissenok88.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lissenok88.restaurant.voting.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lissenok88.restaurant.voting.web.menu.MenuTestData.*;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantController.REST_URL;
import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;
import static ru.lissenok88.restaurant.voting.web.user.UserTestData.*;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuToday() throws Exception {
        restaurant_1.setMenuItems(menu1);
        restaurant_2.setMenuItems(menu2);
        restaurant_3.setMenuItems(menu3);
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menu-items"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_ITEMS_MATCHER.contentJson(List.of(restaurant_1, restaurant_2, restaurant_3)));
    }
}