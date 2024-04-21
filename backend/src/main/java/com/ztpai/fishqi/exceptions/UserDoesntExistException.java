package com.ztpai.fishqi.exceptions;

public class UserDoesntExistException extends Exception{
    public UserDoesntExistException(String msg) {
        super(msg);
    }
}
