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

    @ManyToOne
    @JsonIgnore
    private User customer;

//    @ManyToOne
//    private  Restaurant restaurant;

    public Reservation( LocalDate date, User customer) {
//        this.unique_code = unique_code;
        this.date = date;
        this.customer = customer;
    }
}
