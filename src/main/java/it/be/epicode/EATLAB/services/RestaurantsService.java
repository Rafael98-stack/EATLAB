package it.be.epicode.EATLAB.services;


import it.be.epicode.EATLAB.entities.Restaurant;
import it.be.epicode.EATLAB.entities.Type;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.restaurants.RestaurantCreationDTO;
import it.be.epicode.EATLAB.repositories.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantsService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    public Restaurant saveRestaurant(RestaurantCreationDTO restaurantCreationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (currentUser.getType() == Type.OWNER) {

Restaurant restaurant = new Restaurant(restaurantCreationDTO.logo(), restaurantCreationDTO.description(), restaurantCreationDTO.telephone_contact(), restaurantCreationDTO.seat(), restaurantCreationDTO.address(), restaurantCreationDTO.city(),currentUser);

            return restaurantDAO.save(restaurant);
        } else {
            throw new UnauthorizedException("You are not allowed here, cause from your 'TYPE'");
        }
    }
}
