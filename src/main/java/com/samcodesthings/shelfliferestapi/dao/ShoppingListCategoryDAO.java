package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.ShoppingListCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShoppingListCategoryDAO extends CrudRepository<ShoppingListCategory, String> {

    Set<ShoppingListCategory> findByOrderById();
    Optional<ShoppingListCategory> findById(String id);

}
