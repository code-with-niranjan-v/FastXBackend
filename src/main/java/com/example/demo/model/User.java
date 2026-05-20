package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String phoneNumber;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private double wallet;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "user")
    private List<Refund> refunds;
    @NotBlank
    @NotNull
    @JsonIgnore
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "busOperator")
    private List<Bus> buses;

    @OneToMany(mappedBy = "busOperator")
    private List<Refund> operatorRefunds;

    @JsonIgnore
    private String resetToken;

    @JsonIgnore
    private LocalDateTime resetTokenExpiry;

    @Column(nullable = false)
    private boolean active = true;
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", wallet=" + wallet +
                '}';
    }
}
