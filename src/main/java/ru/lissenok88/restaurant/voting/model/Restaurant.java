package ru.lissenok88.restaurant.voting.model;

import java.util.Date;
import java.util.List;

public class Restaurant extends AbstractNamedEntity{
    private Date date;
    private List<Menu> menu;

    public Restaurant() {

    }

    public Restaurant(Integer id, String name, Date date, List<Menu> menu) {
        super(id, name);
        this.date = date;
        this.menu = menu;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Menu> getMenu() {
        return menu;
    }
}
