package it.be.epicode.EATLAB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.be.epicode.EATLAB.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
