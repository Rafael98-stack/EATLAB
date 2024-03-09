package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {
}
