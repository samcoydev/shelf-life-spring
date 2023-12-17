package com.samcodesthings.shelfliferestapi.service.impl;


import com.samcodesthings.shelfliferestapi.dao.PantryDAO;
import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsItemDTO;
import com.samcodesthings.shelfliferestapi.dto.PantryItemDTO;
import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.exception.NotFoundException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.PantryItem;
import com.samcodesthings.shelfliferestapi.model.Product;
import com.samcodesthings.shelfliferestapi.model.User;
import com.samcodesthings.shelfliferestapi.service.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "pantryService")
@AllArgsConstructor
public class PantryServiceImpl implements PantryService {

    UserService userService;
    ProductService productService;
    PantryDAO pantryDAO;

    @Override
    public PantryItem savePantryItem(PantryItemDTO pantryItemDTO) {
        try {
            PantryItem newPantryItem = new PantryItem();
            User user = userService.findById(getCurrentId(), false);
            Optional<Product> product = productService.findProductByCode(pantryItemDTO.getProduct().getCode());

            if (product.isPresent())
                newPantryItem.setProduct(product.get());
            newPantryItem.setHousehold(user.getHousehold());

            return pantryDAO.save(newPantryItem);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<PantryItem> getPantryItemsByHousehold() {
        try {
            User user = userService.findById(getCurrentId(), false);

            List<PantryItem> pantryList = new ArrayList<>();
            pantryDAO.findPantryItemsByHousehold(user.getHousehold()).iterator().forEachRemaining(pantryList::add);

            log.info("Pantry List returned: " + pantryList);

            return pantryList;
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
