package com.samcodesthings.shelfliferestapi.service;

import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsItemDTO;
import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.exception.NotFoundException;
import com.samcodesthings.shelfliferestapi.model.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findProductByCode(String code);
    Product saveFoodItem(OpenFoodFactsItemDTO foodItem);
    ProductDTO scanProduct(String code) throws Exception;
    ProductDTO requestProductToBeAdded(ProductDTO requestedProductDTO) throws Exception;
}
