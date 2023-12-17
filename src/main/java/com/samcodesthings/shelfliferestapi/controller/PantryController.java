package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.dto.PantryItemDTO;
import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.service.PantryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pantry")
public class PantryController {

    @Autowired
    PantryService pantryService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        log.info("[GET] ping");
        return Collections.singletonMap("response", "pong");
    }

    @GetMapping
    public List<PantryItemDTO> getPantryItems() {
        List<PantryItemDTO> list = modelMapper.map(pantryService.getPantryItemsByHousehold(), new TypeToken<List<PantryItemDTO>>() {}.getType());
        log.info("Mapped items: " + list);
        return list;
    }

    @PostMapping
    public PantryItemDTO savePantryItem(@Valid @RequestBody PantryItemDTO pantryItemDTO) {
        return modelMapper.map(pantryService.savePantryItem(pantryItemDTO), PantryItemDTO.class);
    }

}
