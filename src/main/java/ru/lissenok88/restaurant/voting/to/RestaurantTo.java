package ru.lissenok88.restaurant.voting.to;

import ru.lissenok88.restaurant.voting.model.Menu;

import java.util.List;

public class RestaurantTo {
    private final Integer id;
    private final String name;
    private final List<Menu> menu;
    private final int voteCount;

    public RestaurantTo(Integer id, String name, List<Menu> menu, int voteCount) {
        this.id = id;
        this.name = name;
        this.menu = menu;
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                ", voteCount=" + voteCount +
                '}';
    }
}