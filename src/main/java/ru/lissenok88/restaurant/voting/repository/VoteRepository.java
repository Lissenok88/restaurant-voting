package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.localDate = :localDate AND v.user.id = :userId")
    Optional<Vote> getByDate(LocalDate localDate, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId")
    List<Vote> getByUser(int userId);
}