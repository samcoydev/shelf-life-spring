package com.samcodesthings.shelfliferestapi.service.impl;

import com.samcodesthings.shelfliferestapi.dao.ProductDAO;
import com.samcodesthings.shelfliferestapi.dto.OpenFoodFactsItemDTO;
import com.samcodesthings.shelfliferestapi.dto.PantryItemDTO;
import com.samcodesthings.shelfliferestapi.dto.ProductDTO;
import com.samcodesthings.shelfliferestapi.model.Product;
import com.samcodesthings.shelfliferestapi.service.OpenFoodFactsService;
import com.samcodesthings.shelfliferestapi.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service("productService")
public class ProductServiceImpl implements ProductService {

    ProductDAO productDAO;
    ModelMapper modelMapper;
    OpenFoodFactsService openFoodFactsService;

    @Override
    public Optional<Product> findProductByCode(String code) {
        return productDAO.findById(code);
    }

    @Override
    public ProductDTO scanProduct(String code) throws Exception {
        Optional<Product> product = findProductByCode(code);

        if (product.isPresent() && product.get().getCode() != null) {
            log.info("Scan product: " + product.get());
            return modelMapper.map(product.get(), ProductDTO.class);
        }

        // Essentially caching the product here.
        Optional<OpenFoodFactsItemDTO> itemDTO = openFoodFactsService.getItemByCode(code);
        if (itemDTO.isPresent() && itemDTO.get().getCode() != null) {
            log.info("Caching scanned product: " + itemDTO);
            // Since there is no form yet, we will just automatically save entries
            return modelMapper.map(saveFoodItem(itemDTO.get()), ProductDTO.class);
        }

        // TODO (SAM) Add a form to create new entry. Will need a review system to keep bad entries out.
        log.info("Entry not found in any database, will require a new entry for review");
        return null;
    }

    @Override
    public Product saveFoodItem(OpenFoodFactsItemDTO foodItem) {
        Product newProduct = new Product();

        newProduct.setCode(foodItem.getCode());
        newProduct.setName(foodItem.getName());
        newProduct.setPendingRequestGrant(false);

        return productDAO.save(newProduct);
    }

    @Override
    public ProductDTO requestProductToBeAdded(ProductDTO requestedProductDTO) throws Exception {
        /*
        To note for later - I would like users to choose whether they want to see products
        still pending request at a couple of levels:

        Households can see their own requested items
        - Does the user want to see products pending request from friends' households
        - Does the user want to see all products pending request

        For now, we will allow the user to see all products pending request.
        */
        if (requestedProductDTO.getCode() == null || requestedProductDTO.getName() == null)
            throw new Exception("Requested Product Form had no name or no code");

        Product newProduct = new Product();

        newProduct.setCode(requestedProductDTO.getCode());
        newProduct.setName(requestedProductDTO.getName());
        newProduct.setPendingRequestGrant(true);

        return modelMapper.map(productDAO.save(newProduct), ProductDTO.class);
    }
}
