package it.be.epicode.EATLAB.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue
    private UUID Id;
    private String logo;
    private String description;
    private int rating;
    private String telephone_contact;
    private int seat;
    private String address;
    private String city;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    public Restaurant(String logo, String description, String telephone_contact, int seat, String address, String city, User owner) {
        this.logo = logo;
        this.description= description;
        this.telephone_contact = telephone_contact;
        this.seat = seat;
        this.address = address;
        this.city = city;
        this.owner = owner;
    }
}
