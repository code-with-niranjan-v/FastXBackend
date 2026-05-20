package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnAuthorizedException;
import com.example.demo.model.Booking;
import com.example.demo.model.Bus;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepo;
import com.example.demo.repository.BusRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BusServiceImpl implements BusService{

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public List<Bus> searchBuses(String origin, String destination, String date, String time) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        LocalDateTime start = LocalDateTime.of(localDate,localTime);

        return busRepo.findBuses(origin,destination,localDate,localTime);
    }

    @Override
    public Bus getBusById(int busId) throws ResourceNotFoundException {
        return busRepo.findById(busId).orElseThrow(()->new ResourceNotFoundException("Bus Not Found With ID: "+busId));
    }

    @Override
    public Bus addBus(Bus bus, String email) {
        User operator = userRepo.findByEmail(email);

        bus.setBusOperator(operator);
        busRepo.save(bus);
        return bus;

    }

    public Bus updateBus(int id, Bus updatedBus, String email) throws UnAuthorizedException {

        Bus bus = busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (!bus.getBusOperator().getEmail().equals(email)) {
            throw new UnAuthorizedException("Unauthorized Access Only Owner can update the bus");
        }

        bus.setName(updatedBus.getName());
        bus.setBusType(updatedBus.getBusType());
        bus.setFare(updatedBus.getFare());
        bus.setStatus(updatedBus.getStatus());
        bus.setNoOfSeats(updatedBus.getNoOfSeats());
        return busRepo.save(bus);
    }

    @Override
    public void deleteBus(int id, String operatorEmail) throws UnAuthorizedException {
        Bus bus = busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (!bus.getBusOperator().getEmail().equals(operatorEmail)) {
            throw new UnAuthorizedException("Unauthorized Access Only Owner can update the bus");
        }

        busRepo.delete(bus);
    }

    public List<Bus> getBusesByOperator(String email) {
        return busRepo.findByBusOperator_Email(email);
    }


}
