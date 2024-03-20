package it.be.epicode.EATLAB.services;


import it.be.epicode.EATLAB.entities.Reservation;
import it.be.epicode.EATLAB.entities.Restaurant;
import it.be.epicode.EATLAB.entities.Type;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.reservations.ReservationUpdatingDTO;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantCreationDTO;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantUpdatingDTO;
import it.be.epicode.EATLAB.repositories.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantsService {

    @Autowired
    private RestaurantDAO restaurantDAO;


    public Page<Restaurant> getRestaurants(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return restaurantDAO.findAll(pageable);
    }

    public List<Restaurant> getRestaurantsByUserEmail(String userEmail) {

        return restaurantDAO.findByOwnerEmail(userEmail);
    }

    public Restaurant findByIdAndUpdate(UUID restaurantId, RestaurantUpdatingDTO restaurantUpdatingDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = (User) authentication.getPrincipal();

        Restaurant found = this.findByIdAndUserEmail(restaurantId,userEmail);
        if (currentUser.getType() == Type.CUSTOMER) {
            throw new UnauthorizedException("You are not allowed to Updating a restaurat's info");
        } else { if (found != null) {

found.setLogo(restaurantUpdatingDTO.logo());
     found.setDescription(restaurantUpdatingDTO.description());
found.setTelephone_contact(restaurantUpdatingDTO.telephone_contact());
found.setSeat(restaurantUpdatingDTO.seat());
found.setAddress(restaurantUpdatingDTO.address());
found.setCity(restaurantUpdatingDTO.city());
found.setAvailability(restaurantUpdatingDTO.availability());
            return restaurantDAO.save(found);
        } else {
            throw  new UnauthorizedException("You are not authorized to update this restaurant");
        }
        }
    }

    public Restaurant findByIdAndUserEmail(UUID restaurantId, String userEmail) {
        return restaurantDAO.findByIdAndOwnerEmail(restaurantId, userEmail);
    }

    public Restaurant findById(UUID restaurantId) {
        return restaurantDAO.findById(restaurantId).orElseThrow(() -> new NotFoundException(restaurantId));
    }

    public Restaurant saveRestaurant(RestaurantCreationDTO restaurantCreationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getType() == Type.OWNER) {

Restaurant restaurant = new Restaurant(restaurantCreationDTO.logo(),restaurantCreationDTO.title(), restaurantCreationDTO.description(), restaurantCreationDTO.telephone_contact(), restaurantCreationDTO.seat(), restaurantCreationDTO.address(), restaurantCreationDTO.city(),currentUser);

            return restaurantDAO.save(restaurant);
        } else {
            throw new UnauthorizedException("You are not allowed here, cause from your 'TYPE'");
        }
    }

    public void findByIdAndDelete(UUID restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        if (this.isUserAuthorized(restaurantId, userEmail)) {
        } else {
            throw new UnauthorizedException("You are not authorized to delete this restaurant");
        }
        Restaurant found = this.findById(restaurantId);
        restaurantDAO.delete(found);
    }

    public boolean isUserAuthorized(UUID restaurantId, String userEmail) {

        Restaurant restaurant = restaurantDAO.findById(restaurantId).orElse(null);

        return restaurant != null && restaurant.getOwner().getEmail().equals(userEmail);
    }
}
