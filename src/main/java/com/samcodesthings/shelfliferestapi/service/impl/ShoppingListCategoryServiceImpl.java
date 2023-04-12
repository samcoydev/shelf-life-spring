package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.ShoppingListCategoryDAO;
import com.samcodesthings.shelfliferestapi.dto.ShoppingListCategoryDTO;
import com.samcodesthings.shelfliferestapi.model.ShoppingListCategory;
import com.samcodesthings.shelfliferestapi.service.ShoppingListCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "shoppingListCategoryService")
@AllArgsConstructor
public class ShoppingListCategoryServiceImpl implements ShoppingListCategoryService {

    ShoppingListCategoryDAO shoppingListCategoryDAO;

    @Override
    public List<ShoppingListCategory> findAll() {
        List<ShoppingListCategory> shoppingListCategories = new ArrayList<>();
        shoppingListCategoryDAO.findByOrderById().iterator().forEachRemaining(shoppingListCategories::add);
        return shoppingListCategories;
    }

    @Override
    public ShoppingListCategory findById(String id)  {
        return shoppingListCategoryDAO.findById(id).get();
    }

    @Override
    public ShoppingListCategory save(ShoppingListCategoryDTO shoppingListCategoryDTO) {
        ShoppingListCategory newShoppingListCategory = new ShoppingListCategory();
        newShoppingListCategory.setName(shoppingListCategoryDTO.getName());

        return shoppingListCategoryDAO.save(newShoppingListCategory);
    }

    @Override
    public ShoppingListCategory update(String id, ShoppingListCategoryDTO shoppingListCategoryDTO) {
        Optional<ShoppingListCategory> optionalShoppingListCategory = shoppingListCategoryDAO.findById(id);

        if (optionalShoppingListCategory.isPresent()) {
            ShoppingListCategory existingShoppingListCategory = optionalShoppingListCategory.get();
            existingShoppingListCategory.setName(shoppingListCategoryDTO.getName());
            return shoppingListCategoryDAO.save(existingShoppingListCategory);
        } else {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        ShoppingListCategory shoppingListCategory = shoppingListCategoryDAO.findById(id).get();
        shoppingListCategoryDAO.delete(shoppingListCategory);
    }

}
