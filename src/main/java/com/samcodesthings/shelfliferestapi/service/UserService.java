package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);
    UserDTO findSignedInUser();
    List<User> findByHouseholdId(String householdId);
    User save(UserDTO user);
    void delete(String id);

    User updateHouseholdWithEmail(String email, Household household);
    User welcomeUserWithEmail(String email);

}
