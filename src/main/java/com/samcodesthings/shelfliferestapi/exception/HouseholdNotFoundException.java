package com.samcodesthings.shelfliferestapi.exception;

public class HouseholdNotFoundException extends Exception {
    public HouseholdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
