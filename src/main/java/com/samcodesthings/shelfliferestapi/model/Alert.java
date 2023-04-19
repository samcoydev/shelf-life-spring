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

    @Column(name = "alert_text")
    private String text;

    @ManyToOne()
    @JoinColumn(name = "alerted_household_id")
    @JsonProperty("alerted_household_id")
    private Household alertedHousehold;

    @ManyToOne()
    @JoinColumn(name = "alerted_user_id")
    @JsonProperty("alerted_user_id")
    private User alertedUser;

    private Date expiration;

    private AlertType alertType;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "household_request_id")
    @JsonProperty("household_request_id")
    private HouseholdRequest householdRequest;


    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "friend_request_id")
    @JsonProperty("friend_request_id")
    private FriendRequest friendRequest;

}
