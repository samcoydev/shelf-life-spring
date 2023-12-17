package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.PantryItem;
import com.samcodesthings.shelfliferestapi.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductDAO extends CrudRepository<Product, String> {

    Optional<Product> findById(String id);

}
