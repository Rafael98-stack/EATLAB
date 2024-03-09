package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.payloads.reservations.ReservationDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @PostMapping("/creation/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation saveReservation(@RequestBody ReservationDTO newReservation, @PathVariable UUID userId) {

        return this.reservationsService.saveReservation(newReservation, userId);
    }
}
