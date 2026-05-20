package com.example.demo.service;

import com.example.demo.mapper.RefundMapper;
import com.example.demo.model.Booking;
import com.example.demo.model.Refund;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepo;
import com.example.demo.repository.RefundRepo;
import com.example.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RefundDTO;
import java.util.List;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public List<RefundDTO>
    getRefundRequests(
            String email
    ) {

        User operator =
                userRepo.findByEmail(
                        email
                );

        return refundRepo
                .findByBusOperator(
                        operator
                )
                .stream()
                .map(
                        RefundMapper::toRefundDTO
                )
                .toList();
    }

    @Transactional
    @Override
    public Refund approveRefund(
            int refundId
    ) {

        Refund refund =
                refundRepo.findById(
                        refundId
                ).orElseThrow(
                        () ->
                                new RuntimeException(
                                        "Refund not found"
                                )
                );

        Booking booking =
                refund.getBook();

        User user =
                refund.getUser();

        User operator =
                refund.getBusOperator();

        double amount =
                refund.getAmount();

        user.setWallet(
                user.getWallet()
                        + amount
        );

        operator.setWallet(
                operator.getWallet()
                        - amount
        );

        booking.setStatus(
                "CANCELLED"
        );

        refund.setStatus(
                "COMPLETED"
        );

        userRepo.save(user);

        userRepo.save(operator);

        bookingRepo.save(booking);

        refundRepo.save(refund);

        return refund;
    }

    @Transactional
    @Override
    public Refund rejectRefund(
            int refundId
    ) {

        Refund refund =
                refundRepo.findById(
                        refundId
                ).orElseThrow(
                        () ->
                                new RuntimeException(
                                        "Refund not found"
                                )
                );

        Booking booking =
                refund.getBook();

        booking.setStatus(
                "CONFIRMED"
        );

        refund.setStatus(
                "REJECTED"
        );

        bookingRepo.save(booking);

        refundRepo.save(refund);

        return refund;
    }
}
