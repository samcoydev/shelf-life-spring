package com.samcodesthings.shelfliferestapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PantryItemDTO {

    private String id;

    private ProductDTO product;

    private Date expirationDate;

}
