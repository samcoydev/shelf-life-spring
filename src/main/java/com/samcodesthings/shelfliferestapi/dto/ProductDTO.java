package com.samcodesthings.shelfliferestapi.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String code;

    private String name;

    private boolean isPendingRequestGrant;
}
