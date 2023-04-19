package com.samcodesthings.shelfliferestapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendRequestDTO {

    private String id;

    @NotNull(message="From User cannot be empty")
    private String fromUserId;

    @NotNull(message="To User cannot be empty")
    private String toUserId;
}
