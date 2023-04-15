package com.samcodesthings.shelfliferestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "alerts")
@Data
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "alert_test")
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alerted_household_id")
    @JsonProperty("alerted_household_id")
    private Household alertedHousehold;

    private Date expiration;

    private AlertType alertType;

    @OneToOne(optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "household_request_id")
    @JsonProperty("household_request_id")
    private HouseholdRequest request;

}
