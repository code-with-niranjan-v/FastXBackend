package com.example.demo.repository;

import com.example.demo.model.Refund;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepo extends JpaRepository<Refund,Integer> {
    List<Refund> findByBusOperator(User user);
}
