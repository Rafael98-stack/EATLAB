package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import it.be.epicode.EATLAB.entities.User;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
return currentAuthenticatedUser;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/me")
    public User getCurrentAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User updatedUser){
return this.findByIdAndUpdate(currentAuthenticatedUser.getId(),updatedUser);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrent(@AuthenticationPrincipal User currentAuthenticatedUser){
this.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.usersService.getUsers(page, size, orderBy);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable UUID id) {
        return this.usersService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody User updatedUser) {

        return this.usersService.findByIdAndUpdate(id, updatedUser);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.usersService.findByIdAndDelete(id);
    }

}