package com.samcodesthings.shelfliferestapi.exception;

public class NotAValidRequestException extends Exception {
    public NotAValidRequestException(String errorMessage) {
        super(errorMessage);
    }
}
