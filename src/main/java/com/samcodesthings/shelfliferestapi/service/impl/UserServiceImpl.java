package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.UserDAO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserDAO userDao;

    ModelMapper modelMapper;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).get();
    }

    @Override
    public List<User> findByHouseholdId(String householdId) {
        List<User> userList = new ArrayList<>();
        userDao.findUsersByHouseholdId(householdId).iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public UserDTO findSignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("TESTING: " + username);
        return modelMapper.map(findByEmail("samueljoshuacoy@gmail.com"), UserDTO.class);
    }

    @Override
    public User save(UserDTO user) {
        log.info("SAVE USER: " + user);
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setHasBeenWelcomed(false);

        return userDao.save(newUser);
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
    public User welcomeUserWithEmail(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setHasBeenWelcomed(true);

            return userDao.save(existingUser);
        } else {
            log.info("Couldn't find user with email: " + email);
            return null;
        }
    }

    @Override
    public void delete(String id) {
        User user = userDao.findById(id).get();
        userDao.delete(user);
    }
}

