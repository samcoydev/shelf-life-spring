package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.AlertDAO;
import com.samcodesthings.shelfliferestapi.dao.HouseholdDAO;
import com.samcodesthings.shelfliferestapi.dao.HouseholdRequestDAO;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "alertService")
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    AlertDAO alertDAO;

    HouseholdDAO householdDAO;

    HouseholdRequestDAO householdRequestDAO;

    UserService userService;

    @Override
    public void respondToRequest(String alertId, boolean didAccept) {
        Optional<Alert> alert = alertDAO.findAlertById(alertId);

        if (alert.isEmpty()) {
            log.error("Could not find Alert with ID: " + alertId);
            return;
        } else if (alert.get().getAlertType() != AlertType.REQUEST || alert.get().getRequest() == null) {
            log.error("Alert did not have an attached request or wasn't a request alert");
            return;
        }

        HouseholdRequest request = alert.get().getRequest();

        if (didAccept) {
            userService.updateHouseholdWithId(request.getFromUser().getId(), request.getRequestedHousehold());
            userService.welcomeUserWithEmail(request.getFromUser().getEmail());
            log.info("Accepted request and successfully changed user household");
        } else {
            log.info("Rejected request.");
        }

        alertDAO.delete(alert.get());
    }

    @Override
    public List<Alert> getAlertsByUserId() {
        List<Alert> alerts = new ArrayList<>();
        try {
            Household household = userService.findById(getCurrentId()).getHousehold();
            alertDAO.findAlertsByAlertedHousehold(household).iterator().forEachRemaining(alerts::add);
            return alerts;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Alert save(AlertDTO alertDTO) {
        Alert newAlert = new Alert();

        newAlert.setAlertedHousehold(alertDTO.getHousehold());
        newAlert.setAlertType(alertDTO.getAlertType());
        newAlert.setExpiration(getExpiration(alertDTO));
        newAlert.setText(alertDTO.getText());
        newAlert.setRequest(alertDTO.getRequest());

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
}
