package com.example.demo.exception;

public class UnAuthorizedException extends Exception{
    public UnAuthorizedException(String msg){
        super(msg);
    }
}
