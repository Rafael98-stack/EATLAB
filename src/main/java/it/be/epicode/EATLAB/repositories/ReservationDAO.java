package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerUsername(String username);
}
