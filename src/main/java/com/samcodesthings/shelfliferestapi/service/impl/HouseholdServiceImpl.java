package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.HouseholdDAO;
import com.samcodesthings.shelfliferestapi.dao.HouseholdRequestDAO;
import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.enums.AlertType;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import com.samcodesthings.shelfliferestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Household save(HouseholdDTO householdDTO, String email) {
        if (userService.findByEmail(email) == null)
            log.error("There was no user with email: " + email + " found in the database.");

        Household newHousehold = new Household();
        newHousehold.setName(householdDTO.getName());

        Household savedHouse = householdDAO.save(newHousehold);
        userService.updateHouseholdWithEmail(email, savedHouse);

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
    public HouseholdRequest requestToJoinHousehold(String fromEmail, String householdName) throws Exception {
            if (findByName(householdName).isEmpty())
                throw new Exception("There was no household by the name of: " + householdName);

            Household household = findByName(householdName).get();

            HouseholdRequest newReq = new HouseholdRequest();
            newReq.setRequestedHousehold(household);
            newReq.setFromEmail(fromEmail);
            HouseholdRequest savedReq = householdRequestDAO.save(newReq);

            AlertDTO newAlert = new AlertDTO();
            newAlert.setAlertType(AlertType.REQUEST);
            newAlert.setHousehold(household);
            newAlert.setText("User " + fromEmail + " would like to join your Household");
            newAlert.setRequest(savedReq);
            alertService.save(newAlert);

            return savedReq;
    }

    @Override
    public void delete(String id) {
        Household household = householdDAO.findById(id).get();
        householdDAO.delete(household);
    }

}
