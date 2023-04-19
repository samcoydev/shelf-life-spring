package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.HouseholdDAO;
import com.samcodesthings.shelfliferestapi.dao.HouseholdRequestDAO;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "householdService")
@AllArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    HouseholdDAO householdDAO;

    HouseholdRequestDAO householdRequestDAO;

    UserService userService;

    AlertService alertService;

    @Override
    public List<Household> findAll() {
        List<Household> households = new ArrayList<>();
        householdDAO.findByOrderById().iterator().forEachRemaining(households::add);
        return households;
    }

    @Override
    public Household findById(String id)  {
        return householdDAO.findHouseholdById(id).get();
    }

    @Override
    public Optional<Household> findByName(String name)  {
        log.info("Find by name: " + name);
        return householdDAO.findHouseholdByName(name);
    }

    @Override
    public Household save(HouseholdDTO householdDTO) {
        Household newHousehold = new Household();
        newHousehold.setName(householdDTO.getName());

        Household savedHouse = householdDAO.save(newHousehold);
        userService.updateHouseholdWithId(getCurrentId(), savedHouse);

        return savedHouse;
    }

    @Override
    public Household update(String id, HouseholdDTO householdDTO) {
        Optional<Household> optionalHousehold = householdDAO.findById(id);

        if (optionalHousehold.isPresent()) {
            Household existingHousehold = optionalHousehold.get();
            existingHousehold.setName(householdDTO.getName());
            return householdDAO.save(existingHousehold);
        } else {
            return null;
        }
    }

    @Override
    public HouseholdRequest requestToJoinHousehold(String householdName) throws Exception {
            if (findByName(householdName).isEmpty())
                throw new Exception("There was no household by the name of: " + householdName);

            Household household = findByName(householdName).get();
            User user = userService.findById(getCurrentId());

            HouseholdRequest newReq = new HouseholdRequest();
            newReq.setRequestedHousehold(household);
            newReq.setFromUser(user);
            HouseholdRequest savedReq = householdRequestDAO.save(newReq);

            AlertDTO newAlert = new AlertDTO();
            newAlert.setAlertType(AlertType.REQUEST);
            newAlert.setAlertedHouseholdId(household.getId());
            newAlert.setText("User " + user.getEmail() + " would like to join your Household");
            newAlert.setHouseholdRequestId(savedReq.getId());
            alertService.save(newAlert);

            return savedReq;
    }

    @Override
    public void respondToRequest(String alertId, boolean didAccept) {
        Optional<Alert> alert = alertService.findAlertById(alertId);

        if (alert.isEmpty()) {
            log.error("Could not find Alert with ID: " + alertId);
            return;
        } else if (alert.get().getAlertType() != AlertType.REQUEST || alert.get().getHouseholdRequest() == null) {
            log.error("Alert did not have an attached request or wasn't a request alert");
            return;
        }

        HouseholdRequest request = alert.get().getHouseholdRequest();

        if (didAccept) {
            userService.updateHouseholdWithId(request.getFromUser().getId(), request.getRequestedHousehold());
            userService.welcomeUserWithEmail(request.getFromUser().getEmail());
            log.info("Accepted request and successfully changed user household");
        } else {
            log.info("Rejected request.");
        }

        alertService.delete(alert.get().getId());
    }

    @Override
    public void delete(String id) {
        Household household = householdDAO.findById(id).get();
        householdDAO.delete(household);
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
