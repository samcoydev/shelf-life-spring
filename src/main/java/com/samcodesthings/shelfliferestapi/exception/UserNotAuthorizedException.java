package com.samcodesthings.shelfliferestapi.exception;

public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
