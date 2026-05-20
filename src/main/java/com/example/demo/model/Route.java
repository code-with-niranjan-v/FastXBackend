package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String origin;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String destination;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private Boolean daily = true;

    @Override
    public String toString() {
        return "Route{" +
                "id=" + routeId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", startDateTime=" + startDateTime +
                '}';
    }
}
