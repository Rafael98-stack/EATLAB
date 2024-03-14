package it.be.epicode.EATLAB.services;

import it.be.epicode.EATLAB.entities.*;
import it.be.epicode.EATLAB.exceptions.BadRequestException;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.exceptions.SeatLimitExceededException;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.reservations.ReservationCreationDTO;
import it.be.epicode.EATLAB.payloads.reservations.ReservationUpdatingDTO;
import it.be.epicode.EATLAB.repositories.ReservationDAO;
import it.be.epicode.EATLAB.repositories.RestaurantDAO;
import it.be.epicode.EATLAB.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ReservationsService {

    @Autowired
            private UsersDAO usersDAO;

    Random uniqueRandomCode = new Random();

    private List<Reservation> reservations = new ArrayList<>();

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private RestaurantsService restaurantsService;

    public Page<Reservation> getReservations(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return reservationDAO.findAll(pageable);
    }

    public List<Reservation> getReservationsByUserEmail(String userEmail) {

        return reservationDAO.findByCustomerEmail(userEmail);
    }

    public List<Reservation> getReservationsByRestaurantId(UUID restaurantId) {
        return reservationDAO.findByRestaurantId(restaurantId);
    }

    public Reservation findById(UUID reservationId) {
        return reservationDAO.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    public Reservation saveReservation(ReservationCreationDTO reservationCreationDTO, UUID restaurantId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Restaurant restaurant = restaurantsService.findById(restaurantId);

if (currentUser.getType() == Type.CUSTOMER) {

    LocalDate reservationDate = reservationCreationDTO.date();

    List<Reservation> existingReservations = reservationDAO.findByRestaurantIdAndDate(restaurantId, reservationDate);

    int totalSeatsReserved = existingReservations.stream()
            .mapToInt(Reservation::getPersons)
            .sum();

    int totalSeatsRequested = reservationCreationDTO.persons();
    int maxSeatsAllowed = restaurant.getSeat();

    if (restaurant.getAvailability() == Availability.NOT_AVAILABLE) {
        throw new BadRequestException("This Restaurant is not Available for the moment");
    }

    if (totalSeatsReserved + totalSeatsRequested > maxSeatsAllowed) {
        throw new SeatLimitExceededException("Reservation for this date: " + reservationCreationDTO.date() + " is not available for this restaurant, choose another date.");
    }

    Reservation reservation = new Reservation(reservationCreationDTO.date(), currentUser, reservationCreationDTO.persons(),restaurant);

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
