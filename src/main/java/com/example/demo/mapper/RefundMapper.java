package com.example.demo.mapper;

import com.example.demo.dto.RefundDTO;
import com.example.demo.model.Refund;

public class RefundMapper {

    public static RefundDTO
    toRefundDTO(
            Refund refund
    ) {

        return new RefundDTO(

                refund.getRefundId(),

                refund.getAmount(),

                refund.getStatus(),

                refund.getBook()
                        .getBookingId(),

                refund.getUser()
                        .getName(),

                refund.getBook()
                        .getBus()
                        .getRoute()
                        .getOrigin(),

                refund.getBook()
                        .getBus()
                        .getRoute()
                        .getDestination()
        );
    }
}
