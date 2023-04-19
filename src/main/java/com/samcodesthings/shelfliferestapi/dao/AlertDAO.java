package com.samcodesthings.shelfliferestapi.dao;

import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AlertDAO extends CrudRepository<Alert, String> {

    Set<Alert> findAlertsByAlertedUserOrAlertedHousehold(User user, Household household);
    Optional<Alert> findAlertById(String id);

}
