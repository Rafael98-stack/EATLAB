package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantDAO extends JpaRepository<Restaurant, UUID> {
}
