package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.ShoppingListItemDAO;
import com.samcodesthings.shelfliferestapi.dto.ShoppingListItemDTO;
import com.samcodesthings.shelfliferestapi.model.ShoppingListItem;
import com.samcodesthings.shelfliferestapi.service.ShoppingListItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "shoppingListItemService")
@AllArgsConstructor
public class ShoppingListItemServiceImpl implements ShoppingListItemService {

    ShoppingListItemDAO shoppingListItemDAO;

    @Override
    public List<ShoppingListItem> findAll() {
        List<ShoppingListItem> shoppingListItems = new ArrayList<>();
        shoppingListItemDAO.findByOrderById().iterator().forEachRemaining(shoppingListItems::add);
        return shoppingListItems;
    }

    @Override
    public ShoppingListItem findById(String id)  {
        return shoppingListItemDAO.findById(id).get();
    }

    @Override
    public ShoppingListItem save(ShoppingListItemDTO shoppingListItemDTO) {
        ShoppingListItem newShoppingListItem = new ShoppingListItem();
        newShoppingListItem.setName(shoppingListItemDTO.getName());
        newShoppingListItem.setMarked(shoppingListItemDTO.isMarked());
        newShoppingListItem.setHousehold(shoppingListItemDTO.getHouseHold());
        newShoppingListItem.setCategory(shoppingListItemDTO.getCategory());

        return shoppingListItemDAO.save(newShoppingListItem);
    }

    @Override
    public ShoppingListItem update(String id, ShoppingListItemDTO shoppingListItemDTO) {
        Optional<ShoppingListItem> optionalShoppingListItem = shoppingListItemDAO.findById(id);

        if (optionalShoppingListItem.isPresent()) {
            ShoppingListItem existingShoppingListItem = optionalShoppingListItem.get();
            existingShoppingListItem.setName(shoppingListItemDTO.getName());
            return shoppingListItemDAO.save(existingShoppingListItem);
        } else {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        ShoppingListItem shoppingListItem = shoppingListItemDAO.findById(id).get();
        shoppingListItemDAO.delete(shoppingListItem);
    }

}
