package com.samcodesthings.shelfliferestapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sl_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Household household;

    @Column(name = "hasBeenWelcomed")
    private boolean hasBeenWelcomed;
}
