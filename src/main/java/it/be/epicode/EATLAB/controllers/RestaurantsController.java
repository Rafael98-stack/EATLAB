package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.Restaurant;
import it.be.epicode.EATLAB.payloads.reservations.ReservationCreationDTO;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantCreationDTO;
import it.be.epicode.EATLAB.services.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @PostMapping("/creation")
    public Restaurant createRestaurant(@RequestBody RestaurantCreationDTO restaurantCreationDTO) {


        return restaurantsService.saveRestaurant(restaurantCreationDTO);
    }
}
