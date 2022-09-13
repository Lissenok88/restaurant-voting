package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menuItems m WHERE m.localDate =:localDate")
    List<Restaurant> getAllWithMenuItemsByDate(LocalDate localDate);
}