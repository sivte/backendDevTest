package com.backendtest.similarProducts.infrastructure.adapter.in.rest.mapper;

import com.backendtest.similarProducts.domain.model.Product;
import com.backendtest.similarProducts.infrastructure.adapter.in.rest.dto.ProductResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain Product and REST ProductResponse.
 */
@Component
public class ProductResponseMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailability()
        );
    }
}
