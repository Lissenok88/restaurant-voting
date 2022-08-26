package ru.lissenok88.restaurant.voting.web.menu;

import ru.lissenok88.restaurant.voting.model.Menu;
import ru.lissenok88.restaurant.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.lissenok88.restaurant.voting.web.restaurant.RestaurantTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final LocalDate CURRENT_DATE = LocalDate.now();

    public static final int MENU1_ID = 1;
    public static final int MENU2_ID = 5;
    public static final int MENU3_ID = 7;
    public static final int MENU_NOT_FOUND = 20;

    public static final Menu menu1_1 = new Menu(MENU1_ID, "Салат Цезарь", 75, restaurant_1, CURRENT_DATE);
    public static final Menu menu1_2 = new Menu(MENU1_ID + 1, "Суп-пюре", 125, restaurant_1, CURRENT_DATE);
    public static final Menu menu1_3 = new Menu(MENU1_ID + 2, "Картошка по француски", 150, restaurant_1, CURRENT_DATE);
    public static final Menu menu1_4 = new Menu(MENU1_ID + 3, "Кофе", 50, restaurant_1, CURRENT_DATE);
    public static final Menu menu2_1 = new Menu(MENU2_ID, "Паста с грибами", 125, restaurant_2, CURRENT_DATE);
    public static final Menu menu2_2 = new Menu(MENU2_ID + 1, "Капучино", 150, restaurant_2, CURRENT_DATE);
    public static final Menu menu3_1 = new Menu(MENU3_ID, "Салат Министерский", 75, restaurant_3, CURRENT_DATE);
    public static final Menu menu3_2 = new Menu(MENU2_ID + 1, "Солянка", 150, restaurant_3, CURRENT_DATE);
    public static final Menu menu3_3 = new Menu(MENU3_ID + 2, "Картофельное пюре", 100, restaurant_3, CURRENT_DATE);
    public static final Menu menu3_4 = new Menu(MENU3_ID + 3, "Котлета", 50, restaurant_3, CURRENT_DATE);
    public static final Menu menu3_5 = new Menu(MENU3_ID + 4, "Чай", 30, restaurant_3, CURRENT_DATE);

    public static final List<Menu> menu1 = List.of(menu1_1, menu1_2, menu1_3, menu1_4);
    public static final List<Menu> menu2 = List.of(menu2_1, menu2_2);
    public static final List<Menu> menu3 = List.of(menu3_1, menu3_2, menu3_3, menu3_4, menu3_5);

    public static Menu getNew() {
        return new Menu(null, "Салат Греческий", 100, restaurant_2, CURRENT_DATE);
    }

    public static Menu getUpdated() {
        Menu updatedMenu = menu1_1;
        updatedMenu.setName("Updated dish");
        return updatedMenu;
    }
}