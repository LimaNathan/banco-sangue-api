package wk.banco.sangue.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wk.banco.sangue.api.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}