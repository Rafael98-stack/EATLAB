package it.be.epicode.EATLAB.controllers;



import it.be.epicode.EATLAB.payloads.owners.SignUpOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.payloads.users.AfterLoginUserTokenDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.payloads.users.LoginUserDTO;
import it.be.epicode.EATLAB.services.AuthService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


//    @PostMapping("/login/user")
//    public AfterLoginUserTokenDTO login(@RequestBody LoginUserDTO payload) {
//        return new AfterLoginUserTokenDTO(authService.authenticateUserAndGenerateToken(payload));
//    }

    @PostMapping("/login/user")
    public AfterLoginUserTokenDTO login(@RequestBody LoginUserDTO payload) {
        return new AfterLoginUserTokenDTO(authService.authenticateUserAndGenerateToken(payload),authService.authenticatedUserType(payload));
    }


    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveCustomer(@RequestBody SignUpUserDTO newUser) {

        return this.authService.saveUser(newUser);
    }


    @PostMapping("/register/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveOwner(@RequestBody SignUpOwnerDTO newUser) {

        return this.authService.saveOwner(newUser);
    }

}
