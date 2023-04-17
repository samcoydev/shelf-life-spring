package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.UserDAO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service(value = "userService")
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    @Override
    public UserDTO findByEmail(String email) {
        return mapUser(keycloak.realm(realm).users().searchByEmail(email, true).get(0));
    }

    @Override
    public List<User> findByHouseholdId(String householdId) {
        List<User> userList = new ArrayList<>();
        userDao.findUsersByHouseholdId(householdId).iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public UserDTO findDTOById(String id) {
        log.info("Searching for user with id: " + id);
        return mapUser(keycloak.realm(realm).users().get(id).toRepresentation());
    }

    @Override
    public UserRepresentation findRepById(String id) {
        log.info("Searching for user with id: " + id);
        return keycloak.realm(realm).users().get(id).toRepresentation();
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private UserDTO mapUser(UserRepresentation user) {
        UserDTO mappedUser = new UserDTO();

        mappedUser.setId(user.getId());
        mappedUser.setEmail(user.getEmail());
        mappedUser.setFirstName(user.getFirstName());
        mappedUser.setLastName(user.getLastName());
        Map<String, List<String>> attributes = user.getAttributes();

        if (attributes != null) {
            mappedUser.setHouseholdId(attributes.get("household_id").get(0));
            mappedUser.setHasBeenWelcomed(Boolean.parseBoolean(attributes.get("hasBeenWelcomed").get(0)));
        }

        return mappedUser;
    }

    @Override
    public UserDTO findSignedInUser() {
        return findDTOById(getCurrentId());
    }

    @Override
    public User updateHouseholdWithEmail(String email, Household household) {
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHousehold(household);

            return userDao.save(existingUser);
        } else {
            log.info("Couldn't find user with email: " + email);
            return null;
        }
    }

    @Override
    public UserDTO welcomeUser() {
        UserRepresentation user = findRepById(getCurrentId());

        Map<String, List<String>> currentAttributes = user.getAttributes();
        currentAttributes.put("hasBeenWelcomed", Collections.singletonList("true"));

        user.setAttributes(currentAttributes);

        user.

            return userDao.save(existingUser);
        } else {
            log.info("Couldn't find user with email: " + email);
            return null;
        }
    }
}

