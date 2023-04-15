package com.samcodesthings.shelfliferestapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "household_requests")
@Data
public class HouseholdRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "from_email")
    private String fromEmail;

    @ManyToOne(optional = false)
    private Household requestedHousehold;
}
