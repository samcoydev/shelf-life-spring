package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.PantryItemDTO;
import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.exception.NotFoundException;
import com.samcodesthings.shelfliferestapi.model.PantryItem;
import com.samcodesthings.shelfliferestapi.model.Product;

import java.util.List;

public interface PantryService {

    PantryItem savePantryItem(PantryItemDTO productItemDTO);
    List<PantryItem> getPantryItemsByHousehold();

}
