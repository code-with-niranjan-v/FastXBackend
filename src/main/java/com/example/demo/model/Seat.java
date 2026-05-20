package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    @Positive
    @Column(nullable = false)
    private int seatNo;
    @ManyToOne
    private Booking booking;


    @Override
    public String toString() {
        return "Seat{" +
                "id=" + seatId +
                ", seatNo=" + seatNo +
                '}';
    }
}
