//package it.be.epicode.EATLAB.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Entity
//@NoArgsConstructor
//@Table(name = "restaurants")
//public class Restaurant {
//
//    @Id
//    @GeneratedValue
//    private long Id;
//    private String logo;
//    private String description;
//    private int rating;
//    private String telephone_contact;
//    private int seat;
//    private String address;
//    private String city;
//
//    @ManyToOne
//    private User owner;
//
//    @Enumerated(EnumType.STRING)
//    private Availability availability;
//
//@OneToMany
//private Reservation reservation;
//
//@ManyToOne
//private User user;
//
//    public Restaurant(String logo, String description, int rating, String telephone_contact, int seat, String address, String city, User owner, Availability availability, Reservation reservation) {
//        this.logo = logo;
//        this.description = description;
//        this.rating = rating;
//        this.telephone_contact = telephone_contact;
//        this.seat = seat;
//        this.address = address;
//        this.city = city;
//        this.owner = owner;
//        this.availability = availability;
//        this.reservation = reservation;
//    }
//}
