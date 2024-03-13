package it.be.epicode.EATLAB.payloads.restaurants;

public record RestaurantUpdatingDTO(String logo,
                                    String description,
                                    String telephone_contact,
                                    int seat,
                                    String address,
                                    String city) {
}
