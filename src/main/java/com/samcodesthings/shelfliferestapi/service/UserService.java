package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.NotAValidRequestException;
import com.samcodesthings.shelfliferestapi.exception.UserNotAuthorizedException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;

import java.util.List;

public interface UserService {

    UserDTO findByEmail(String email) throws UserNotFoundException;
    UserDTO findSignedInUser() throws UserNotFoundException;
    User findById(String id, boolean createNewUser) throws UserNotFoundException;
    List<User> findByHouseholdId(String householdId);

    List<AlertDTO> getUserAlerts();
    List<UserDTO> getUsersFriends();

    void sendFriendRequest(String userEmail) throws UserNotFoundException;
    void respondToRequest(String alertId, boolean didAccept) throws AlertNotFoundException, NotAValidRequestException;

    User updateHouseholdWithId(String id, Household household) throws UserNotFoundException;
    UserDTO welcomeUser() throws UserNotFoundException;
    UserDTO markUserAsWelcomedByEmail(String email) throws UserNotFoundException;

    void logout() throws UserNotFoundException;
    void deleteUserAlert(String id) throws AlertNotFoundException, UserNotAuthorizedException;

}
