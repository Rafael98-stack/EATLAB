package it.be.epicode.EATLAB.controllers;

import it.be.epicode.EATLAB.entities.Owner;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.services.OwnersService;
import it.be.epicode.EATLAB.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnersController {

    @Autowired
    private OwnersService ownersService;

    @GetMapping
    public Page<Owner> getAllOwners(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.ownersService.getOwners(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Owner findById(@PathVariable UUID id) {
        return this.ownersService.findById(id);
    }

    @PutMapping("/{id}")
    public Owner findByIdAndUpdate(@PathVariable UUID id, @RequestBody Owner updatedOwner) {

        return this.ownersService.findByIdAndUpdate(id, updatedOwner);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.ownersService.findByIdAndDelete(id);
    }

}