package ru.lissenok88.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"user_id", "local_date"}, name = "vote_unique_user_id_local_date")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    public Vote(Restaurant restaurant, LocalDate localDate) {
        this(null, restaurant, localDate);
    }

    public Vote(Integer id, Restaurant restaurant, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.localDate = localDate;
    }
}