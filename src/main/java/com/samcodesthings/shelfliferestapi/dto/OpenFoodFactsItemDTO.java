package com.samcodesthings.shelfliferestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenFoodFactsItemDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("product_name")
    private String name;

}
