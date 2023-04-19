package com.samcodesthings.shelfliferestapi.exception;

public class AlertNotFoundException extends Exception {
    public AlertNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
