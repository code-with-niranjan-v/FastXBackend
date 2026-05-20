package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int refundId;
    private double amount;
    private String status;
    @OneToOne
    private Booking book;
    @ManyToOne
    private User busOperator;
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Refund{" +
                "refundId=" + refundId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", book=" + book +
                ", busOperator=" + busOperator +
                '}';
    }
}
