package it.be.epicode.EATLAB.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.payloads.users.AfterLoginUserTokenDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.payloads.users.LoginUserDTO;
import it.be.epicode.EATLAB.services.AuthService;
import it.be.epicode.EATLAB.services.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UsersService usersService;


    @PostMapping("/login")
    public AfterLoginUserTokenDTO login(@RequestBody LoginUserDTO payload) {
        return new AfterLoginUserTokenDTO(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody SignUpUserDTO newUser) {

        return this.authService.saveUser(newUser);
    }

}
