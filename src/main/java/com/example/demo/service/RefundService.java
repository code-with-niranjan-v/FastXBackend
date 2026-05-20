package com.example.demo.service;

import com.example.demo.dto.RefundDTO;
import com.example.demo.model.Refund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RefundService {
    List<RefundDTO>
    getRefundRequests(
            String email
    );

    Refund approveRefund(int refundId);

    Refund rejectRefund(int refundId);
}
