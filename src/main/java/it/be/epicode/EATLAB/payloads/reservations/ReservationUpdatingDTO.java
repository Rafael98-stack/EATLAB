package it.be.epicode.EATLAB.payloads.reservations;

import java.time.LocalDate;

public record ReservationUpdatingDTO(LocalDate date,
                                     int persons) {
}
