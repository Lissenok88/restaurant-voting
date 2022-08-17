package ru.lissenok88.restaurant.voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"name", "restaurant_id", "local_date"}, name = "menu_unique_name_local_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Menu extends NamedEntity {
    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private Integer price;

    @Column(name = "local_date", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    public Menu(String name, Integer price, Restaurant restaurant, LocalDate localDate) {
        this(null, name, price, restaurant, localDate);
    }

    public Menu(Integer id, String name, Integer price, Restaurant restaurant, LocalDate localDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }
}