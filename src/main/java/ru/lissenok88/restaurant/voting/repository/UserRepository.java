package ru.lissenok88.restaurant.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lissenok88.restaurant.voting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}