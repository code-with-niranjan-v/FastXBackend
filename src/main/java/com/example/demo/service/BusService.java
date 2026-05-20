package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnAuthorizedException;
import com.example.demo.model.Booking;
import com.example.demo.model.Bus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusService {
    List<Bus> searchBuses(String origin, String destination, String date, String time);

    Bus getBusById(int busId) throws ResourceNotFoundException;

    Bus addBus(Bus bus, String email);

    Bus updateBus(int id, Bus updatedBus, String operatorEmail) throws UnAuthorizedException;
    void deleteBus(int id, String operatorEmail) throws UnAuthorizedException;
    List<Bus> getBusesByOperator(String email);


}
