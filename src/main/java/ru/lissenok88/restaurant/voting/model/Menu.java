package ru.lissenok88.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"restaurant_id", "local_date", "name"}, name = "menu_items_unique_restaurant_id_local_date_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends NamedEntity {
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "local_date", nullable = false, columnDefinition = "date default now()", updatable = false)
    @NotNull
    private LocalDate localDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Restaurant restaurant;

    public Menu(Integer id, String name, Integer price, Restaurant restaurant, LocalDate localDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }
}