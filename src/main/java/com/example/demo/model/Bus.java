package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busId;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotBlank
    @NotNull
    @Column(nullable = false,unique = true)
    private String busNumber;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String busType;
    @Positive
    @Column(nullable = false)
    private int noOfSeats;
    @Positive
    @Column(nullable = false)
    private double fare;
    @Column(nullable = false)
    private boolean waterBottle;
    @Column(nullable = false)
    private boolean blanket;
    @Column(nullable = false)
    private boolean chargingPoint;
    @Column(nullable = false)
    private boolean tv;
    @ManyToOne
    @JsonIgnore
    private User busOperator;
    @ManyToOne
    private Route route;
    @Column(nullable = false)
    private String status = "ON ROUTE";


    @Override
    public String toString() {
        return "Bus{" +
                "id=" + busId +
                ", name='" + name + '\'' +
                ", busNumber='" + busNumber + '\'' +
                ", busType='" + busType + '\'' +
                ", noOfSeats=" + noOfSeats +
                ", fare=" + fare +
                ", waterBottle=" + waterBottle +
                ", blanket=" + blanket +
                ", chargingPoint=" + chargingPoint +
                ", tv=" + tv +
                '}';
    }


}
