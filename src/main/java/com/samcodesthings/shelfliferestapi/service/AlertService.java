package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;

import java.util.List;

public interface AlertService {

    List<Alert> getAlertsByUserEmailsHousehold(String email);

    Alert save(AlertDTO alertDTO);
    void delete (String id);


}