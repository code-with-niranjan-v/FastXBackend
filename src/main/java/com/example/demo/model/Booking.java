package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    @Positive
    @Column(nullable = false)
    private double totalFare;
    @Positive
    @Column(nullable = false)
    private int totalNoOfSeats;
    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String status;
    @ManyToOne
    private Bus bus;
    @ManyToOne
    private User busOperator;
    @ManyToOne
    private User user;
    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL
    )
    private List<Seat> seats;

    @NotNull
    @Column(nullable = false)
    private LocalDate journeyDate;
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", totalFare=" + totalFare +
                ", totalNoOfSeats=" + totalNoOfSeats +
                ", status='" + status + '\'' +
                ", bus=" + bus +
                ", busOperator=" + busOperator +
                '}';
    }
}
