package it.be.epicode.EATLAB.repositories;

import it.be.epicode.EATLAB.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, UUID> {

    Reservation findByIdAndCustomerEmail(UUID reservationId, String userEmail);

    List<Reservation> findByCustomerEmail(String email);
}
