package com.example.demo.controller;

import com.example.demo.response.ApiResponse;
import com.example.demo.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operator/refund")
@PreAuthorize(
        "hasAuthority('ROLE_OPERATOR')"
)
public class RefundController {

    @Autowired
    private RefundService refundService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>>
    getRefundRequests(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        true,
                        "Refund requests fetched",
                        refundService
                                .getRefundRequests(
                                        authentication.getName()
                                )
                )
        );
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<?>>
    approveRefund(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        true,
                        "Refund approved",
                        refundService
                                .approveRefund(id)
                )
        );
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<?>>
    rejectRefund(
            @PathVariable int id
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        true,
                        "Refund rejected",
                        refundService
                                .rejectRefund(id)
                )
        );
    }
}
