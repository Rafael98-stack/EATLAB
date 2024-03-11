package it.be.epicode.EATLAB.services;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.payloads.reservations.ReservationDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.repositories.ReservationDAO;
import it.be.epicode.EATLAB.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ReservationsService {

    @Autowired
            private UsersDAO usersDAO;

    Random uniqueRandomCode = new Random();

    @Autowired
    private ReservationDAO reservationDAO;

    public Page<Reservation> getReservations(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return reservationDAO.findAll(pageable);
    }


    public List<Reservation> getReservationsByUserEmail(String userEmail) {

        return reservationDAO.findByCustomerEmail(userEmail);
    }

    public Reservation findById(UUID reservationId) {
        return reservationDAO.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    public Reservation saveReservation(Reservation reservation) {
        reservation.setUnique_code(  uniqueRandomCode.nextLong(100000,500000));
        return reservationDAO.save(reservation);
    }

    public Reservation findByIdAndUpdate(UUID reservationId, Reservation modifiedReservation) {
        Reservation found = this.findById(reservationId);
        found.setUnique_code(modifiedReservation.getUnique_code());
        found.setDate(modifiedReservation.getDate());
        found.setCustomer(modifiedReservation.getCustomer());
        return reservationDAO.save(found);
    }

    public Reservation findByIdAndUserEmail(UUID reservationId, String userEmail) {
        return reservationDAO.findByIdAndCustomerEmail(reservationId, userEmail);
    }

    public void findByIdAndDelete(UUID reservationId) {
        Reservation found = this.findById(reservationId);
        reservationDAO.delete(found);
    }

    public boolean isUserAuthorized(UUID reservationId, String userEmail) {

        Reservation reservation = reservationDAO.findById(reservationId).orElse(null);

        return reservation != null && reservation.getCustomer().getEmail().equals(userEmail);
    }
}
