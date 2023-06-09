package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.HouseholdDTO;
import com.samcodesthings.shelfliferestapi.dto.RequestResponseDTO;
import com.samcodesthings.shelfliferestapi.exception.AlertNotFoundException;
import com.samcodesthings.shelfliferestapi.exception.NotAValidRequestException;
import com.samcodesthings.shelfliferestapi.exception.UserNotFoundException;
import com.samcodesthings.shelfliferestapi.model.Household;
import com.samcodesthings.shelfliferestapi.model.HouseholdRequest;
import com.samcodesthings.shelfliferestapi.service.HouseholdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/household")
@AllArgsConstructor
public class HouseholdController {

    private HouseholdService householdService;

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        log.info("[GET] ping");
        return Collections.singletonMap("response", "pong");
    }

    @PostMapping()
    public Household saveHousehold(@Valid @RequestBody HouseholdDTO householdDTO) throws UserNotFoundException {
        log.info("[POST] New Household");
        return householdService.save(householdDTO);
    }

    @PostMapping("/request/{name}")
    public HouseholdRequest requestToJoinHousehold(@PathVariable("name") String householdName) throws Exception {
        log.info("[POST] Requested to join Household: " + householdName);
        return householdService.requestToJoinHousehold(householdName);
    }

    @PostMapping(path = "/request/action")
    public void respondToRequest(@Valid @RequestBody RequestResponseDTO requestResponseDTO) throws UserNotFoundException, NotAValidRequestException, AlertNotFoundException {
        log.info("[POST] Respond to request");
        householdService.respondToRequest(requestResponseDTO.getAlertId(), requestResponseDTO.isDidAccept());
    }

}
