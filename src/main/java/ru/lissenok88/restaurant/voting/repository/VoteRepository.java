package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.User;
import ru.lissenok88.restaurant.voting.model.Vote;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.localDate =: localDate AND v.user =: user")
    Vote getByUserByDate(@Param("localDate") LocalDate localDate, @Param("user") User user);
}