package com.samcodesthings.shelfliferestapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    private String code;

    private String name;

    private boolean isPendingRequestGrant;

}
