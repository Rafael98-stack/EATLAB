package it.be.epicode.EATLAB.services;

import it.be.epicode.EATLAB.entities.Owner;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.repositories.OwnersDAO;
import it.be.epicode.EATLAB.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnersService {

    @Autowired
    private OwnersDAO ownersDAO;

    public Page<Owner> getOwners(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return ownersDAO.findAll(pageable);
    }

    public Owner findById(UUID userId) {
        return ownersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public Owner findByIdAndUpdate(UUID ownerId, Owner modifiedOwner) {
        Owner found = this.findById(ownerId);
        found.setSurname(modifiedOwner.getSurname());
        found.setName(modifiedOwner.getName());
        found.setEmail(modifiedOwner.getEmail());
        found.setPassword(modifiedOwner.getPassword());
        return ownersDAO.save(found);
    }

    public void findByIdAndDelete(UUID ownerId) {
        Owner found = this.findById(ownerId);
        ownersDAO.delete(found);
    }

    public Owner findByEmail(String email) {
        return ownersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " non trovata"));
    }

}
