package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.model.Household;

import java.util.List;

public interface HouseholdService {

    Household save(HouseholdDTO householdDTO, String email);
    Household update(String id, HouseholdDTO householdDTO);
    void delete (String id);

    List<Household> findAll();
    Household findById(String id);

}
