package com.samcodesthings.shelfliferestapi.model;

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

    @ManyToOne
    private Household alertedHousehold;

    private Date expiration;

    private AlertType alertType;
}
