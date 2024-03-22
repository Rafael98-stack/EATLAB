package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.Restaurant;
import it.be.epicode.EATLAB.entities.Type;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantCreationDTO;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantUpdatingDTO;
import it.be.epicode.EATLAB.services.ReservationsService;
import it.be.epicode.EATLAB.services.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private ReservationsService reservationsService;


    @PostMapping("/creation")
    public Restaurant createRestaurant(@RequestBody RestaurantCreationDTO restaurantCreationDTO) {

        return restaurantsService.saveRestaurant(restaurantCreationDTO);
    }


    @GetMapping("/myrestaurants")
    public ResponseEntity<List<Restaurant>> getMyRestaurants() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getType() == Type.OWNER) {
            List<Restaurant> restaurants = restaurantsService.getRestaurantsByUserEmail(userEmail);



            for (Restaurant restaurant : restaurants) {
                List<Reservation> reservations = reservationsService.getReservationsByRestaurantId(restaurant.getId());
                restaurant.setReservations(reservations);
            }

            return ResponseEntity.ok(restaurants);
        } else {
            throw new UnauthorizedException("Unable to return your list of reservations, you are not a Owner");

        }
    }


        @GetMapping
        public Page<Restaurant> getAllRestaurants ( @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String orderBy
    ){
            return this.restaurantsService.getRestaurants(page, size, orderBy);
        }


    @PutMapping("/{restaurantId}")
    public Restaurant updateReservation(@PathVariable UUID restaurantId, @RequestBody RestaurantUpdatingDTO restaurantUpdatingDTO) {

        return restaurantsService.findByIdAndUpdate(restaurantId,restaurantUpdatingDTO);

    }


    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@PathVariable UUID restaurantId) {

        restaurantsService.findByIdAndDelete(restaurantId);
    }
}
