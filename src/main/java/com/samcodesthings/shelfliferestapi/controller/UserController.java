package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.FriendRequestDTO;
import com.samcodesthings.shelfliferestapi.dto.RequestResponseDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.NotAValidRequestException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.FriendRequest;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    /**
     * Returns a simple JSON response intended to confirm server connectivity.
     *
     * @return A Map object containing the "response" key and "pong" value.
     */
    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        log.info("[GET] ping");
        return Collections.singletonMap("response", "pong");
    }

    /**
     * Returns a UserDTO object that contains information about the currently signed-in user.
     * The information includes the user's name, email, and other relevant details.
     *
     * @return A UserDTO object containing the signed-in user's information.
     */
    @GetMapping()
    public UserDTO getUserData() throws UserNotFoundException {
        log.info("[GET] Find User");
        return userService.findSignedInUser();
    }

    /**
     * Logs out the currently signed-in user.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() throws UserNotFoundException {
        userService.logout();
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }

    /**
     * Returns a list of UserDTO objects that represent the friends of the signed-in user.
     *
     * @return A List of UserDTO objects representing the signed-in user's friends.
     */
    @GetMapping("/friends")
    public List<UserDTO> getUsersFriends() {
        log.info("[GET] Finding users friends");
        return userService.getUsersFriends();
    }

    /**
     * Sends a friend request to the user with the specified email address.
     *
     * @param userEmail The email address of the user to send the friend request to.
     */
    @PostMapping("/friends/{userEmail}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable("userEmail") String userEmail) throws UserNotFoundException {
        log.info("[POST] Sending friend request to " + userEmail);
        userService.sendFriendRequest(userEmail);
        return new ResponseEntity<>("Successfully sent a friend request to " + userEmail, HttpStatus.OK);
    }

    /**
     * Responds to a friend request that was sent to the user
     *
     * @param requestResponseDTO The response containing the acceptance or rejection to the request.
     */
    @PostMapping(path = "/friends/action")
    public ResponseEntity<String> respondToRequest(@Valid @RequestBody RequestResponseDTO requestResponseDTO) throws NotAValidRequestException, AlertNotFoundException {
        log.info("[POST] Respond to friend request");
        userService.respondToRequest(requestResponseDTO.getAlertId(), requestResponseDTO.isDidAccept());
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }

    /**
     * Returns a list of User objects that represent the users who belong to the same household as the signed-in user.
     *
     * @param householdId The ID of the household to get the users for.
     * @return A List of User objects representing the users in the specified household.
     */
    @GetMapping("/household/{householdId}")
    public List<User> getUsersByHouseholdId(@PathVariable("householdId") String householdId) {
        log.info("[GET] Users of same household " + householdId);
        return userService.findByHouseholdId(householdId);
    }

    /**
     * Sets the signed-in user has been welcomed status to true. This is to avoid them seeing the "intro" or "welcome"
     * screen in the app after they've already gone through it once.
     *
     * @return A UserDTO object representing the newly welcomed user.
     */
    @PostMapping("/welcome")
    public UserDTO welcomeUser() throws UserNotFoundException {
        log.info("[POST] Welcome User");
        return userService.welcomeUser();
    }

    /**
     * Updates the household of the currently signed-in user to null, effectively removing them from the household.
     *
     * @return A User object representing the updated user.
     */
    @PutMapping("/household/leave")
    public User leaveHousehold() throws UserNotFoundException {
        log.info("[PUT] Leave Household");
        return userService.updateHouseholdWithId(getCurrentId(), null);
    }

    /**
     * Gets the alerts for the signed-in user, and their household.
     *
     * @return A List of Alert objects representing the alerts for the signed-in user.
     */
    @GetMapping("/alerts")
    public List<AlertDTO> getAlertsById() {
        log.info("[GET] User Alerts");
        return userService.getUserAlerts();
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}