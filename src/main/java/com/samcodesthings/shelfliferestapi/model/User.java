package com.samcodesthings.shelfliferestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sl_user")
@Data
public class User {

    @Id
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(optional = true)
    @JoinColumn(name = "household_id")
    @JsonProperty("household_id")
    private Household household;

    @Column(name = "has_been_welcomed")
    private boolean hasBeenWelcomed;
}
