package it.be.epicode.EATLAB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.NotFoundException;
import it.be.epicode.EATLAB.repositories.UsersDAO;

import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;

    public Page<User> getUsers(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }

    public User findById(UUID userId) {
        return usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, User modifiedUser) {
        User found = this.findById(userId);
        found.setSurname(modifiedUser.getSurname());
        found.setName(modifiedUser.getName());
        found.setEmail(modifiedUser.getEmail());
        found.setPassword(modifiedUser.getPassword());
        return usersDAO.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        usersDAO.delete(found);
    }

    public User findByEmail(String email) {
        return usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " non trovata"));
    }

}
