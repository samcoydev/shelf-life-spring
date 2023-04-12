package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.ShoppingListCategoryDTO;
import com.samcodesthings.shelfliferestapi.model.ShoppingListCategory;

import java.util.List;

public interface ShoppingListCategoryService {

    ShoppingListCategory save(ShoppingListCategoryDTO shoppingListCategoryDTO);
    ShoppingListCategory update(String id, ShoppingListCategoryDTO shoppingListCategoryDTO);
    void delete (String id);

    List<ShoppingListCategory> findAll();
    ShoppingListCategory findById(String id);

}
