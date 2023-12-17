package com.samcodesthings.shelfliferestapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsItemDTO;
import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsResponseDTO;
import com.samcodesthings.shelfliferestapi.service.OpenFoodFactsService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
@Service("openFoodFactsService")
public class OpenFoodFactsServiceImpl implements OpenFoodFactsService {

    private final static String BASE_URL = "https://world.openfoodfacts.org/api/v2";

    @Override
    public Optional<OpenFoodFactsItemDTO> getItemByCode(String code) {
        log.info("Getting item by scanned code: " + code + " from API");
        String uri = BASE_URL + "/search?fields=code,product_name&page_size=1&complete=1&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OpenFoodFactsResponseDTO responseDTO = objectMapper.readValue(response, OpenFoodFactsResponseDTO.class);

            List<OpenFoodFactsItemDTO> products = responseDTO.getProducts();
            OpenFoodFactsItemDTO item = products.get(0);
            log.info("API Item: " + item);

            if (item != null && item.getCode() != null)
                return Optional.of(item);

        } catch (Exception e) {
            log.error("There was a problem getting the item with code: " + code + " from the Open Food Facts API: " + e.getMessage());
            return Optional.empty();
        }

        log.error("Defaulting to empty");
        return Optional.empty();
    }

}
