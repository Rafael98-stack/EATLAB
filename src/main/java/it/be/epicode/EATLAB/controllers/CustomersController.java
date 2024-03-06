package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import it.be.epicode.EATLAB.entities.User;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class CustomersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.usersService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return this.usersService.findById(id);
    }

    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody User updatedUser) {

        return this.usersService.findByIdAndUpdate(id, updatedUser);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.usersService.findByIdAndDelete(id);
    }

}