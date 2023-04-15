package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;

import java.util.List;
import java.util.Optional;

public interface HouseholdService {

    Household save(HouseholdDTO householdDTO, String email);
    Household update(String id, HouseholdDTO householdDTO);
    void delete (String id);

    List<Household> findAll();
    Optional<Household> findByName(String name);
    Household findById(String id);

    HouseholdRequest requestToJoinHousehold(String fromEmail, String householdName) throws Exception;

}
