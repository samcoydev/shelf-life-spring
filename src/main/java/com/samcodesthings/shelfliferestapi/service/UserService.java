package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserService {

    UserDTO findByEmail(String email);
    User findById(String id) throws Exception;
    UserDTO findSignedInUser();
    List<User> findByHouseholdId(String householdId);

    User updateHouseholdWithId(String id, Household household);
    UserDTO welcomeUser();
    UserDTO welcomeUserWithEmail(String email);

    void logout();

}
