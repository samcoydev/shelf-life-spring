package com.samcodesthings.shelfliferestapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shopping_list_item")
@Data
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "marked")
    private boolean marked;

    @ManyToOne
    private Household household;

    @ManyToOne
    private ShoppingListCategory category;

}
