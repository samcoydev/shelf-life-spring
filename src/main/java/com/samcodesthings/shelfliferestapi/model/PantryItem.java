package com.samcodesthings.shelfliferestapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "pantry_items")
@Data
public class PantryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Household household;

    private Date expirationDate;

}
