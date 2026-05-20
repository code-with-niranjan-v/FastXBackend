package com.example.demo.exception;

public class InsufficientWalletException extends Exception{

    public InsufficientWalletException(){
        super("Insufficient Funds!");
    }
}
