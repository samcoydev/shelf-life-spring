package com.samcodesthings.shelfliferestapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestResponseDTO {

    @NotNull(message="alertId cannot be empty.")
    private String alertId;

    @NotNull(message="didAccept cannot be empty.")
    private boolean didAccept;

}
