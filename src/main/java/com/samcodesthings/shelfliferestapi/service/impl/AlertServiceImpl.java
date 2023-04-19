package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.*;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.UserDTO;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.model.*;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "alertService")
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    AlertDAO alertDAO;

    HouseholdDAO householdDAO;

    UserDAO userDAO;

    HouseholdRequestDAO householdRequestDAO;

    FriendRequestDAO friendRequestDAO;

    @Override
    public List<Alert> findUserAlerts(User user) {
        List<Alert> alerts = new ArrayList<>();
        alertDAO.findAlertsByAlertedUserOrAlertedHousehold(user, user.getHousehold()).iterator().forEachRemaining(alerts::add);
        return alerts;
    }

    @Override
    public Alert findAlertById(String id) throws AlertNotFoundException {
        Optional<Alert> alert = alertDAO.findAlertById(id);

        if (alert.isEmpty())
            throw new AlertNotFoundException(notFoundAlertWithID(id));

        return alert.get();
    }

    @Override
    public Alert save(AlertDTO alertDTO) {
        Alert newAlert = new Alert();

        newAlert.setAlertType(alertDTO.getAlertType());
        newAlert.setExpiration(getExpiration(alertDTO));
        newAlert.setText(alertDTO.getText());

        if (alertDTO.getAlertedHouseholdId() != null) {
            Optional<Household> alertedHousehold = householdDAO.findHouseholdById(alertDTO.getAlertedHouseholdId());
            alertedHousehold.ifPresent(newAlert::setAlertedHousehold);
        }

        if (alertDTO.getAlertedUserId() != null) {
            Optional<User> alertedUser = userDAO.findById(alertDTO.getAlertedUserId());
            alertedUser.ifPresent(newAlert::setAlertedUser);
        }

        if (alertDTO.getHouseholdRequestId() != null) {
            Optional<HouseholdRequest> hr = householdRequestDAO.findById(alertDTO.getHouseholdRequestId());
            hr.ifPresent(newAlert::setHouseholdRequest);
        }
        if (alertDTO.getFriendRequestId() != null) {
            Optional<FriendRequest> fr = friendRequestDAO.findById(alertDTO.getFriendRequestId());
            fr.ifPresent(newAlert::setFriendRequest);
        }

        return alertDAO.save(newAlert);
    }

    @Override
    public void delete(String id) {
        Alert alert = alertDAO.findById(id).get();
        alertDAO.delete(alert);
    }

    private Date getExpiration(AlertDTO alertDTO) {
        if (alertDTO.getAlertType() == AlertType.EXPIRY_ALERT) {
            Date date = new Date();
            long dayAhead = date.getTime() + (24 * 60 * 60 * 1000);

            return new Date(dayAhead);
        }
        return null;
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private String notFoundAlertWithID(String id) {
        return "Could not find Alert with ID: " + id;
    }
}
