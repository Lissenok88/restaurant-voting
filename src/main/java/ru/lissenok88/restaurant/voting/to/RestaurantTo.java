package ru.lissenok88.restaurant.voting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.lissenok88.restaurant.voting.model.Menu;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo{
    List<Menu> menus;
    int voteCount;

    public RestaurantTo(Integer id, String name, List<Menu> menus, int voteCount) {
        super(id, name);
        this.menus = menus;
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus=" + menus +
                ", voteCount=" + voteCount +
                '}';
    }
}