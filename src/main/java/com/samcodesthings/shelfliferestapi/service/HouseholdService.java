package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.HouseholdNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.NotAValidRequestException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;

import java.util.List;
import java.util.Optional;

public interface HouseholdService {

    Household save(HouseholdDTO householdDTO) throws UserNotFoundException;
    Household update(String id, HouseholdDTO householdDTO);
    void delete (String id) throws HouseholdNotFoundException;

    List<Household> findAll();
    Optional<Household> findByName(String name);
    Household findById(String id);

    HouseholdRequest requestToJoinHousehold(String householdName) throws HouseholdNotFoundException, UserNotFoundException;
    void respondToRequest(String alertId, boolean didAccept) throws AlertNotFoundException, NotAValidRequestException, UserNotFoundException;

}
