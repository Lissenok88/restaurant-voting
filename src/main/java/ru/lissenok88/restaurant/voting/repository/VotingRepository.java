package ru.lissenok88.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.Voting;

@Repository
@Transactional(readOnly = true)
public interface VotingRepository extends JpaRepository<Voting, Integer> {
}
