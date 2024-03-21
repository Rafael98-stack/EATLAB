package it.be.epicode.EATLAB.payloads.restaurants;

import it.be.epicode.EATLAB.entities.Availability;

public record RestaurantUpdatingDTO(String logo,
                                    String title,
                                    String description,
                                    String telephone_contact,
                                    int seat,
                                    String address,
                                    String city,
                                    Availability availability) {
}
