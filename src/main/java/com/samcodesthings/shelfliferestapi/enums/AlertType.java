package com.samcodesthings.shelfliferestapi.enums;

public enum AlertType {
    INVITATION ("Invitation"),
    EXPIRY_ALERT ("Expiry Alert");

    private final String state;

    AlertType(String state  ) {
        this.state = state;
    }

    private String getState() {
        return this.state;
    }
}
