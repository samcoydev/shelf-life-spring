package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.Household;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface HouseholdDAO extends CrudRepository<Household, String> {

    Set<Household> findByOrderById();
    Optional<Household> findHouseholdById(String id);
    Optional<Household> findHouseholdByName(String name);

}