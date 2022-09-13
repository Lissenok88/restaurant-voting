package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.error.IllegalRequestDataException;
import ru.lissenok88.restaurant.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<Menu> get(int id, int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAll(int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id =:restaurantId AND m.localDate =:localDate ORDER BY m.id")
    List<Menu> getAllByDate(int restaurantId, LocalDate localDate);

    default Menu checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Menu id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}