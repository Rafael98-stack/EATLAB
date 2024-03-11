package it.be.epicode.EATLAB.payloads.reservations;

import java.time.LocalDate;

public record ReservationCreationDTO(LocalDate date,
                                     int persons) {

}
