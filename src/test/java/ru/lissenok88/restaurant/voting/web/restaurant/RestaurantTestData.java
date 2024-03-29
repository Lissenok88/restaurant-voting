package ru.lissenok88.restaurant.voting.web.restaurant;

import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_ITEMS_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_1 = 1;
    public static final int RESTAURANT_2 = 2;
    public static final int RESTAURANT_3 = 3;

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_1, "Paris-Way");
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_2, "Italy-Way");
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_3, "Russia-Way");

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1, "UpdatedName");
    }
}