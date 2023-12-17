package com.samcodesthings.shelfliferestapi.controller;

import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/scan{code}")
    public ProductDTO scanItem(@PathVariable("code") String code) throws Exception {
        log.info("[GET] Scan Item");
        return productService.scanProduct(code);
    }

    @PostMapping("/request")
    public ProductDTO requestProductToBeAdded(@RequestBody ProductDTO requestedProductDTO) throws Exception {
        log.info("[POST] Adding Request for Product: " + requestedProductDTO.getName());
        return productService.requestProductToBeAdded(requestedProductDTO);
    }

}
