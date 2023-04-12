package com.samcodesthings.shelfliferestapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShoppingListCategoryDTO {

    private String id;

    @NotNull(message="Name cannot be empty.")
    private String name;

}
