package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        log.info("[GET] ping");
        return Collections.singletonMap("response", "pong");
    }

    @GetMapping()
    public UserDTO getUserData() {
        log.info("[GET] Find User");
        return userService.findSignedInUser();
    }

    @GetMapping("/household{householdId}")
    public List<User> getUsersOfByHouseholdId(@PathVariable("householdId") String householdId) {
        log.info("[GET] Users of same household " + householdId);
        return userService.findByHouseholdId(householdId);
    }

    @PostMapping("/register")
    public User saveUser(@Valid @RequestBody UserDTO user) {
        log.info("[POST] New User");
        return userService.save(user);
    }

    @PostMapping(path = "/welcome")
    public User welcomeUser(@Valid @RequestBody String email) {
        log.info("[POST] Welcome User");
        return userService.welcomeUserWithEmail(email);
    }

    @PutMapping("/household")
    public User leaveHousehold(@RequestHeader("User-Email") String email) {
        log.info("[PUT] Leave Household: " + email);
        return userService.updateHouseholdWithEmail(email, null);
    }
}