package com.ztpai.fishqi.exceptions;

public class ExpiredRefreshTokenException extends Exception {
    public ExpiredRefreshTokenException(String msg) {
        super(msg);
    }
}
