package com.samcodesthings.shelfliferestapi.exception;

public class NotFoundException extends Exception {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
