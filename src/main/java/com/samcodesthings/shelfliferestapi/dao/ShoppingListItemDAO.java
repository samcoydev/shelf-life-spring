package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.ShoppingListItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ShoppingListItemDAO extends CrudRepository<ShoppingListItem, String> {

    Set<ShoppingListItem> findByOrderById();
    Optional<ShoppingListItem> findById(String id);

}
