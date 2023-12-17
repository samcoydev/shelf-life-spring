package com.samcodesthings.shelfliferestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenFoodFactsResponseDTO {

    @JsonProperty("count")
    private int count;

    @JsonProperty("page")
    private int page;

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("products")
    private List<OpenFoodFactsItemDTO> products;

    @JsonProperty("skip")
    private int skip;

}
