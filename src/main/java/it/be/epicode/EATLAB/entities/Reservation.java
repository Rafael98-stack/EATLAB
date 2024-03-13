package it.be.epicode.EATLAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;
    private long unique_code;
private LocalDate date;
private int persons;

    @ManyToOne
    @JsonIgnore
    private User customer;

    @ManyToOne
    @JsonIgnoreProperties({"reservations"})
    private  Restaurant restaurant;

    public Reservation( LocalDate date, User customer, int persons, Restaurant restaurant) {
        this.date = date;
        this.customer = customer;
        this.persons = persons;
        this.restaurant = restaurant;
    }
}
