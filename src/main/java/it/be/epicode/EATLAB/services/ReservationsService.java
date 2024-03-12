package it.be.epicode.EATLAB.services;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.Type;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.reservations.ReservationCreationDTO;
import it.be.epicode.EATLAB.payloads.reservations.ReservationUpdatingDTO;
import it.be.epicode.EATLAB.repositories.ReservationDAO;
import it.be.epicode.EATLAB.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Reservation saveReservation(ReservationCreationDTO reservationCreationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
if (currentUser.getType() == Type.CUSTOMER) {
    Reservation reservation = new Reservation(reservationCreationDTO.date(), currentUser, reservationCreationDTO.persons());

    reservation.setUnique_code(  uniqueRandomCode.nextLong(100000,500000));
    return reservationDAO.save(reservation);
} else {
    throw new  UnauthorizedException ("You are not allowed here, cause from your 'TYPE'");
}
    }

    public Reservation findByIdAndUpdate(UUID reservationId, Reservation modifiedReservation) {
        Reservation found = this.findById(reservationId);
        found.setUnique_code(modifiedReservation.getUnique_code());
        found.setDate(modifiedReservation.getDate());
        found.setCustomer(modifiedReservation.getCustomer());
        return reservationDAO.save(found);
    }

    public Reservation findByIdAndUpdate(UUID reservationId, ReservationUpdatingDTO updatingReservation) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
User currentUser = (User) authentication.getPrincipal();

        Reservation found = this.findByIdAndUserEmail(reservationId,userEmail);
        if (currentUser.getType() == Type.OWNER) {
            throw new UnauthorizedException("You are not allowed to Updating a customer's info");
        } else { if (found != null) {

            if (updatingReservation.persons() == 0) {
                found.setPersons(found.getPersons());
            } else {
                found.setPersons(updatingReservation.persons());
            }

            if (  updatingReservation.date() == null) {
                found.setDate(found.getDate());
            } else {
                found.setDate(updatingReservation.date());
            }

            return reservationDAO.save(found);
        } else {
            throw  new UnauthorizedException("You are not authorized to update this reservation");
        }
        }
    }

    public Reservation findByIdAndUserEmail(UUID reservationId, String userEmail) {
        return reservationDAO.findByIdAndCustomerEmail(reservationId, userEmail);
    }

    public void findByIdAndDelete(UUID reservationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        if (this.isUserAuthorized(reservationId, userEmail)) {
        } else {
            throw new UnauthorizedException("You are not authorized to delete this reservation");
        }
        Reservation found = this.findById(reservationId);
        reservationDAO.delete(found);
    }

    public boolean isUserAuthorized(UUID reservationId, String userEmail) {

        Reservation reservation = reservationDAO.findById(reservationId).orElse(null);

        return reservation != null && reservation.getCustomer().getEmail().equals(userEmail);
    }
}
