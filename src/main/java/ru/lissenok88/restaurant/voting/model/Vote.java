package ru.lissenok88.restaurant.voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"user_id", "restaurant_id", "date_time"}, name = "vote_unique_user_restaurant_date_time")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDate localDate;

    public Vote(Restaurant restaurant, User user, LocalDate localDate) {
        this(null, restaurant, user, localDate);
    }

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.localDate = localDate;
    }
}