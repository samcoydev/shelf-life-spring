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
    public User getUserByEmail(@RequestHeader("User-Email") String email) {
        log.info("[GET] Find User");
        return userService.findByEmail(email);
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

    @PostMapping("/request-household")
    public void requestToJoinHousehold(@Valid @RequestBody String requestEmail, @RequestHeader("User-Email") String email) {
        log.info(email + " is requesting to join " + requestEmail + "'s household.");
        userService.householdRequestToUserFromUser(requestEmail, email);

    }
}