package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.FriendRequestDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserService {

    UserDTO findByEmail(String email);
    User findById(String id) throws Exception;
    UserDTO findSignedInUser();
    List<User> findByHouseholdId(String householdId);

    List<AlertDTO> getUserAlerts();

    List<UserDTO> getUsersFriends();
    void sendFriendRequest(String userEmail);
    void respondToRequest(String alertId, boolean didAccept);

    User updateHouseholdWithId(String id, Household household);
    UserDTO welcomeUser();
    UserDTO welcomeUserWithEmail(String email);

    void logout();

}
