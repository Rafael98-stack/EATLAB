package it.be.epicode.EATLAB.payloads.reservations;

import it.be.epicode.EATLAB.entities.User;

import java.time.LocalDate;
import java.util.List;

public record ReservationDTO(LocalDate date) {
}
