package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.AlertDTO;
import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.User;

import java.util.List;
import java.util.Optional;

public interface AlertService {

    /**
     * Saves the given AlertDTO to the database.
     *
     * @param alertDTO the AlertDTO to be saved
     * @return the saved Alert object
     */
    Alert save(AlertDTO alertDTO);

    /**
     * Deletes the Alert with the specified ID from the database.
     *
     * @param id the ID of the Alert to be deleted
     */
    void delete(String id);

    /**
     * Retrieves a list of Alerts associated with the specified User, and their household.
     *
     * @param user the User object whose Alerts are to be retrieved
     * @return a List of Alert objects associated with the specified User
     */
    List<Alert> findUserAlerts(User user);

    /**
     * Retrieves the Alert with the specified ID, if it exists.
     *
     * @param id the ID of the Alert to be retrieved
     * @return an Alert with the specified ID, if it exists;
     *         otherwise, throws an error.
     */
    Alert findAlertById(String id) throws AlertNotFoundException;

}
