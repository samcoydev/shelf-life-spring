package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.AlertDAO;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service(value = "alertService")
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    AlertDAO alertDAO;

    UserService userService;

    @Override
    public List<Alert> getAlertsByUserEmailsHousehold(String email) {
        List<Alert> alerts = new ArrayList<>();
        alertDAO.findAlertsByAlertedHousehold(userService.findByEmail(email).getHousehold()).iterator().forEachRemaining(alerts::add);
        return alerts;
    }

    @Override
    public Alert save(AlertDTO alertDTO) {
        Alert newAlert = new Alert();

        newAlert.setAlertedHousehold(alertDTO.getHousehold());
        newAlert.setAlertType(alertDTO.getAlertType());
        newAlert.setExpiration(getExpiration(alertDTO));
        newAlert.setText(alertDTO.getText());

        return alertDAO.save(newAlert);
    }

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

}
