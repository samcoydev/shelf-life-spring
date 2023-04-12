package com.samcodesthings.shelfliferestapi.dto;

import com.samcodesthings.shelfliferestapi.model.Household;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    private String id;

    @NotNull(message="Email cannot be empty.")
    private String email;

    private boolean hasBeenWelcomed;

}
