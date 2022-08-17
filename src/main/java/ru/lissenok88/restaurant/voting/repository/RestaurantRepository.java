package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu ld WHERE ld.localDate =: localDate")
    List<Restaurant> getAllWithMenuByDate(@Param("localDate") LocalDate localDate);
}