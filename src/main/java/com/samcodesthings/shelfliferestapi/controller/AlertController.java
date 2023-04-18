package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.RequestResponseDTO;
import com.samcodesthings.shelfliferestapi.model.Alert;
import com.samcodesthings.shelfliferestapi.service.AlertService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/alert")
@AllArgsConstructor
public class AlertController {

    private AlertService alertService;

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        log.info("[GET] ping");
        return Collections.singletonMap("response", "pong");
    }

    @GetMapping()
    public List<Alert> getAlertsById() {
        log.info("[GET] Alerts");
        return alertService.getAlertsByUserId();
    }

    @PostMapping(path = "/action")
    public void respondToRequest(@Valid @RequestBody RequestResponseDTO requestResponseDTO) {
        log.info("[POST] Respond to request");
        alertService.respondToRequest(requestResponseDTO.getAlertId(), requestResponseDTO.isDidAccept());
    }

}
