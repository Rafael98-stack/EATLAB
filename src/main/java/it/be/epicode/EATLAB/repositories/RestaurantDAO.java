package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantDAO extends JpaRepository<Restaurant, UUID> {

    Restaurant findByIdAndOwnerEmail(UUID restaurantId, String userEmail);

    List<Restaurant> findByOwnerEmail(String email);
}
