package ru.lissenok88.restaurant.voting.util;

import ru.lissenok88.restaurant.voting.model.Restaurant;
import ru.lissenok88.restaurant.voting.model.Vote;
import ru.lissenok88.restaurant.voting.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants, List<Vote> votes) {
        return restaurants.stream().map(restaurant -> createTo(restaurant, votes)).collect(Collectors.toList());
    }

    private static RestaurantTo createTo(Restaurant restaurant, List<Vote> votes) {
        return new RestaurantTo(restaurant.id(), restaurant.getName(), restaurant.getMenus(),
                (int) votes.stream().filter(vote -> vote.getRestaurant().getId() == restaurant.getId()).count());
    }
}