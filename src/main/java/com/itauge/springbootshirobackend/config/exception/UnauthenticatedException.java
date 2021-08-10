package com.itauge.springbootshirobackend.config.exception;

/**
 * 未登錄的異常
 */
public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException() {
        super("未登錄");
    }

}
