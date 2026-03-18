package com.example.wordmanagefilesystem.Common.Except;

public class BusinessExcept extends RuntimeException{
    private Integer code;

    public BusinessExcept(Integer code) {
        this.code = code;
    }

    public BusinessExcept(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BusinessExcept(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessExcept(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public BusinessExcept(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
