package com.samcodesthings.shelfliferestapi.dto;

import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.ShoppingListCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShoppingListItemDTO {

    private String id;

    @NotNull(message="Name cannot be empty.")
    private String name;

    private boolean marked;

    private Household houseHold;

    private ShoppingListCategory category;

}
