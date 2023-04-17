package com.samcodesthings.shelfliferestapi.dto;

import com.samcodesthings.shelfliferestapi.model.Household;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    private String id;

    @NotNull(message="Email cannot be empty.")
    private String email;

    @NotNull(message="First Name cannot be empty")
    private String firstName;

    @NotNull(message="Last Name cannot be empty")
    private String lastName;

    private String householdId;

    private boolean hasBeenWelcomed;

}
