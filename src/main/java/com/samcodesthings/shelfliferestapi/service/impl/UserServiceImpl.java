package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.UserDAO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return modelMapper.map(userDao.findByEmail(email), UserDTO.class);
    }

    @Override
    public List<User> findByHouseholdId(String householdId) {
        List<User> userList = new ArrayList<>();
        userDao.findUsersByHouseholdId(householdId).iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public User findById(String id) throws Exception {
        Optional<User> user = userDao.findById(id);

        if (user.isEmpty())
            return createUserRecord(id);

        return user.get();
    }

    @Override
    public UserDTO findSignedInUser() {
        try {
            return modelMapper.map(findById(getCurrentId()), UserDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public User createUserRecord(String id) throws Exception {
        UserRepresentation user = keycloak.realm(realm).users().get(id).toRepresentation();

        if (user == null) {
            log.error("No user in keycloak found by id: " + id);
            throw new Exception("No user in keycloak found by id: " + id);
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
    public User updateHouseholdWithId(String id, Household household) {
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHousehold(household);

            return userDao.save(existingUser);
        } else {
            log.info("Couldn't find user with email: " + id);
            return null;
        }
    }

    @Override
    public UserDTO welcomeUserWithEmail(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHasBeenWelcomed(true);
            return modelMapper.map(userDao.save(existingUser), UserDTO.class);
        } else {
            log.info("Couldn't find user with email: " + email);
            return null;
        }
    }

    @Override
    public UserDTO welcomeUser() {
        Optional<User> optionalUser = userDao.findById(getCurrentId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHasBeenWelcomed(true);
            return modelMapper.map(userDao.save(existingUser), UserDTO.class);
        } else {
            log.info("Couldn't find user with id: " + getCurrentId());
            return null;
        }
    }

    @Override
    public void logout() {
         UserResource user = keycloak.realm(realm).users().get(getCurrentId());

         if (user != null)
             user.logout();
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

