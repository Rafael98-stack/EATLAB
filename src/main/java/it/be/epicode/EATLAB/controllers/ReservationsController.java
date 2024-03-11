package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.reservations.ReservationDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @PostMapping("/creation")
    public Reservation createReservation(@RequestBody ReservationDTO reservationDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

              Reservation reservation = new Reservation(reservationDTO.date(), currentUser);

        return reservationsService.saveReservation(reservation);
    }

    @GetMapping("/myreservations")
    public ResponseEntity<List<Reservation>> getMyReservations() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();


        List<Reservation> reservations = reservationsService.getReservationsByUserEmail(userEmail);


        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation(@PathVariable UUID reservationId, @RequestBody Reservation modifiedReservation) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Reservation existingReservation = reservationsService.findByIdAndUserEmail(reservationId, userEmail);

        if (existingReservation != null) {

            existingReservation.setUnique_code(modifiedReservation.getUnique_code());
            existingReservation.setDate(modifiedReservation.getDate());

            return reservationsService.saveReservation(existingReservation);
        } else {
            throw new UnauthorizedException("You are not authorized to Update this reservation");
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Reservation> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.reservationsService.getReservations(page, size, orderBy);
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable UUID reservationId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        if (reservationsService.isUserAuthorized(reservationId, userEmail)) {
            reservationsService.findByIdAndDelete(reservationId);
        } else {
             throw new UnauthorizedException("You are not authorized to delete this reservation");
        }
    }

}
