package com.ztpai.fishqi.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
