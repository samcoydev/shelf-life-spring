package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.ShoppingListItemDTO;
import com.samcodesthings.shelfliferestapi.model.ShoppingListItem;

import java.util.List;

public interface ShoppingListItemService {

    ShoppingListItem save(ShoppingListItemDTO ShoppingListItemDTO);
    ShoppingListItem update(String id, ShoppingListItemDTO ShoppingListItemDTO);
    void delete (String id);

    List<ShoppingListItem> findAll();
    ShoppingListItem findById(String id);

}
