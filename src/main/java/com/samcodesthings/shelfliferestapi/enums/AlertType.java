package com.samcodesthings.shelfliferestapi.enums;

public enum AlertType {
    REQUEST ("Request"),
    EXPIRY_ALERT ("Expiry Alert");

    private final String state;

    AlertType(String state  ) {
        this.state = state;
    }

    private String getState() {
        return this.state;
    }
}
