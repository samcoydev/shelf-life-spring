package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserDAO extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);

    Set<User> findByHousehold(Household household);
}
