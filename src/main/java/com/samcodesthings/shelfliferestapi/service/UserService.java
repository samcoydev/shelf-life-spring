package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserService {

    UserDTO findByEmail(String email);
    UserDTO findDTOById(String id);
    UserRepresentation findRepById(String id);
    UserDTO findSignedInUser();
    List<User> findByHouseholdId(String householdId);

    User updateHouseholdWithEmail(String email, Household household);
    UserDTO welcomeUser();

}
