package com.samcodesthings.shelfliferestapi.dto;

import com.samcodesthings.shelfliferestapi.enums.AlertType;
import lombok.Data;

import java.util.Date;

@Data
public class AlertDTO {

    private String id;

    private String alertedHouseholdId;

    private String alertedUserId;

    private String text;

    private Date expiration;

    private AlertType alertType;

    private String householdRequestId;

    private String friendRequestId;

}
