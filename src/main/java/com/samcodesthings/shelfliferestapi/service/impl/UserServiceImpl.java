package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.FriendRequestDAO;
import com.samcodesthings.shelfliferestapi.dao.UserDAO;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.NotAValidRequestException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.*;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "userService")
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    AlertService alertService;

    @Autowired
    UserDAO userDao;

    @Autowired
    FriendRequestDAO friendRequestDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userDao.findByEmail(email);

        if (user.isEmpty())
            throw new UserNotFoundException(notFoundUserWithEmailMessage(email));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<User> findByHouseholdId(String householdId) {
        List<User> userList = new ArrayList<>();
        userDao.findUsersByHouseholdId(householdId).iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public UserDTO findSignedInUser() throws UserNotFoundException {
        return modelMapper.map(findById(getCurrentId(), true), UserDTO.class);
    }

    @Override
    public User findById(String id, boolean createNewUser) throws UserNotFoundException {
        Optional<User> user = userDao.findById(id);

        if (user.isEmpty()) {
            if (createNewUser)
                return createUserRecord(id);
            else {
                throw new UserNotFoundException(notFoundUserWithIdMessage(id));
            }
        }

        return user.get();
    }

    public User createUserRecord(String id) throws UserNotFoundException {
        UserRepresentation user = keycloak.realm(realm).users().get(id).toRepresentation();

        if (user == null) {
            log.error("No user in keycloak found by id: " + id);
            throw new UserNotFoundException("No User registered with ID: " + id);
        }

        log.info("SAVE USER: " + user);

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setHasBeenWelcomed(false);

        return userDao.save(newUser);
    }

    @Override
    public User updateHouseholdWithId(String id, Household household) throws UserNotFoundException {
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHousehold(household);

            return userDao.save(existingUser);
        } else {
            log.info("Could not find User with ID: " + id);
            throw new UserNotFoundException(notFoundUserWithIdMessage(id));
        }
    }

    @Override
    public UserDTO markUserAsWelcomedByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHasBeenWelcomed(true);
            return modelMapper.map(userDao.save(existingUser), UserDTO.class);
        } else {
            log.info("Couldn't find user with email: " + email);
            throw new UserNotFoundException(notFoundUserWithEmailMessage(email));
        }
    }

    @Override
    public UserDTO welcomeUser() throws UserNotFoundException {
        Optional<User> optionalUser = userDao.findById(getCurrentId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHasBeenWelcomed(true);
            return modelMapper.map(userDao.save(existingUser), UserDTO.class);
        } else {
            log.info("Couldn't find user with id: " + getCurrentId());
            throw new UserNotFoundException(notFoundUserWithIdMessage(getCurrentId()));
        }
    }

    @Override
    public List<UserDTO> getUsersFriends() {
        Optional<User> optionalUser = userDao.findById(getCurrentId());

        if (optionalUser.isEmpty())
            return new ArrayList<>();

        return modelMapper.map(optionalUser.get().getFriendsList(), new TypeToken<List<UserDTO>>() {}.getType());

    }

    @Override
    public List<AlertDTO> getUserAlerts() {
        Optional<User> optionalUser = userDao.findById(getCurrentId());

        if (optionalUser.isEmpty())
            return new ArrayList<>();

        return modelMapper.map(alertService.findUserAlerts(optionalUser.get()),  new TypeToken<List<AlertDTO>>() {}.getType());
    }

    @Override
    public void sendFriendRequest(String userEmail) throws UserNotFoundException {
        Optional<User> fromUser = userDao.findById(getCurrentId());
        Optional<User> toUser = userDao.findByEmail(userEmail);

        if (toUser.isEmpty()) {
            log.error("Could not find the user to send this request to by ID");
            throw new UserNotFoundException(
                    "There was a problem finding the User that this request is to: "
                            + notFoundUserWithIdMessage(getCurrentId()));
        }

        if (fromUser.isEmpty()) {
            log.error("Could not find the user who sent this request by ID");
            throw new UserNotFoundException(
                    "There was a problem finding the User that created this request: "
                            + notFoundUserWithEmailMessage(userEmail));
        }

        FriendRequest request = new FriendRequest();

        request.setToUser(toUser.get());
        request.setFromUser(fromUser.get());
        FriendRequest req = friendRequestDAO.save(request);

        AlertDTO newAlert = new AlertDTO();
        newAlert.setAlertType(AlertType.REQUEST);
        newAlert.setText("User " + fromUser.get().getEmail() + " would like to be your friend");
        newAlert.setAlertedUserId(toUser.get().getId());
        newAlert.setAlertedHouseholdId(null);
        newAlert.setFriendRequestId(req.getId());

        alertService.save(newAlert);
    }

    @Override
    public void respondToRequest(String alertId, boolean didAccept) throws AlertNotFoundException, NotAValidRequestException {
        Alert alert = alertService.findAlertById(alertId);

        if (alert.getAlertType() != AlertType.REQUEST || alert.getFriendRequest() == null) {
            throw new NotAValidRequestException("Alert with ID: " + alertId + " is not a valid friend request");
        }

        FriendRequest request = alert.getFriendRequest();

        if (didAccept) {
            request.getToUser().addFriend(request.getFromUser());
            request.getFromUser().addFriend(request.getToUser());
            userDao.save(request.getToUser());
            userDao.save(request.getFromUser());

            AlertDTO newAlert = new AlertDTO();
            newAlert.setAlertedUserId(request.getToUser().getId());
            newAlert.setText(request.getToUser().getEmail() + " has accepted your friend request!");
            newAlert.setAlertType(AlertType.NOTIFICATION);
            alertService.save(newAlert);

            log.info("Accepted friend request");
        } else {
            log.info("Rejected friend request");
        }

        alertService.delete(alert.getId());
    }

    @Override
    public void logout() throws UserNotFoundException {
         UserResource user = keycloak.realm(realm).users().get(getCurrentId());

         if (user == null)
             throw new UserNotFoundException("User with ID: " + getCurrentId() + " is not registered");

         user.logout();
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private String notFoundUserWithIdMessage(String id) {
        return "Could not find User with ID: " + id;
    }

    private String notFoundUserWithEmailMessage(String email) {
        return "Could not find User with email: " + email;
    }
}

