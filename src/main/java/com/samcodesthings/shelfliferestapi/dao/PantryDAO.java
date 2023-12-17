package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.PantryItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PantryDAO extends CrudRepository<PantryItem, String> {

    Optional<PantryItem> findById(String id);
    Set<PantryItem> findPantryItemsByHousehold(Household household);

}
