package com.samcodesthings.shelfliferestapi.dto;

import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;
import lombok.Data;

import java.util.Date;

@Data
public class AlertDTO {

    private Household household;

    private String text;

    private Date expiration;

    private AlertType alertType;

    private HouseholdRequest request;

}
