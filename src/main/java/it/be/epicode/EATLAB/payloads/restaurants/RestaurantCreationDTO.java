package it.be.epicode.EATLAB.payloads.restaurants;

public record RestaurantCreationDTO(String logo,
                                    String title,
                                    String description,
                                    String telephone_contact,
                                    int seat,
                                    String address,
                                    String city) {
}
