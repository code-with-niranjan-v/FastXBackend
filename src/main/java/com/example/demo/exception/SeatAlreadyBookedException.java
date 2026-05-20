package com.example.demo.exception;

public class SeatAlreadyBookedException extends Exception{
    public SeatAlreadyBookedException(String msg){
        super(msg);
    }
}
