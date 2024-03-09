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

    public Reservation findById(long reservationId) {
        return reservationDAO.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    public Reservation saveReservation(ReservationDTO payload,UUID userId) {
User user = usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));

        Reservation newReservation = new Reservation( payload.date(),user);
newReservation.setUnique_code(  uniqueRandomCode.nextLong(100000,500000));
        return reservationDAO.save(newReservation);
    }

    public Reservation findByIdAndUpdate(long reservationId, Reservation modifiedReservation) {
        Reservation found = this.findById(reservationId);
        found.setUnique_code(modifiedReservation.getUnique_code());
        found.setDate(modifiedReservation.getDate());
        found.setCustomer(modifiedReservation.getCustomer());
        return reservationDAO.save(found);
    }

    public void findByIdAndDelete(long reservationId) {
        Reservation found = this.findById(reservationId);
        reservationDAO.delete(found);
    }
}
