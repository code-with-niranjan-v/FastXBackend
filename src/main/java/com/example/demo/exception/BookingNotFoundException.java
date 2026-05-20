package com.example.demo.exception;

public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(int id){
        super("Booking Not Found with Id "+id);
    }
}
