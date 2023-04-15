package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdRequestDAO extends CrudRepository<HouseholdRequest, String> {

}
