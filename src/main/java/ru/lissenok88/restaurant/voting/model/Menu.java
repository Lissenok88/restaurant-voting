package ru.lissenok88.restaurant.voting.model;

public class Menu extends AbstractBaseEntity{
    private String dish;
    private Integer price;

    public Menu(){

    }

    public Menu(Integer id, String dish, Integer price) {
        super(id);
        this.dish = dish;
        this.price = price;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
