package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Owner;
import it.be.epicode.EATLAB.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnersDAO extends JpaRepository<Owner, UUID> {
    Optional<Owner> findByEmail(String email);

    boolean existsByEmail(String email);
}
