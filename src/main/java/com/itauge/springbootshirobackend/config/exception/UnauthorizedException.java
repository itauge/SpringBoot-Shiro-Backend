package com.itauge.springbootshirobackend.config.exception;

/**
 * 訪問權限不足
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("用戶無此接口權限");
    }
}
