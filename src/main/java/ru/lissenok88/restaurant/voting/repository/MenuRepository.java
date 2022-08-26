package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Menu;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<Menu> get(int id, int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAll(int restaurantId);
}