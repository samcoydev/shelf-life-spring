package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsItemDTO;

import java.util.Optional;

public interface OpenFoodFactsService {

    Optional<OpenFoodFactsItemDTO> getItemByCode(String code);

}
