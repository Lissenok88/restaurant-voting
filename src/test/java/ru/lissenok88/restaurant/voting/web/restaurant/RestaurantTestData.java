package ru.lissenok88.restaurant.voting.web.restaurant;

import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.to.RestaurantTo;
import ru.lissenok88.restaurant.voting.web.MatcherFactory;

import static ru.lissenok88.restaurant.voting.web.menu.MenuTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_WITH_MENUS_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "menus.restaurant");

    public static final int RESTAURANT_1 = 1;
    public static final int RESTAURANT_2 = 2;
    public static final int RESTAURANT_3 = 3;

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_1, "Paris-Way");
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_2, "Italy-Way");
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_3, "Russia-Way");

    static {
        restaurant_1.setMenus(menu1);
        restaurant_2.setMenus(menu2);
        restaurant_3.setMenus(menu3);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1, "UpdatedName");
    }
}