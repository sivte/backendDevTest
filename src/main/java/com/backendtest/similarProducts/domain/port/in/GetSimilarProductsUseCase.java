package com.backendtest.similarProducts.domain.port.in;

import com.backendtest.similarProducts.domain.model.Product;

import java.util.List;

/**
 * Input port 
 */
public interface GetSimilarProductsUseCase {
    
    /**
     * Get similar products for a given product ID.
     *
     * @param productId the product ID to find similar products for
     * @return list of similar products
     * @throws com.backendtest.similarProducts.domain.exception.ProductNotFoundException if product not found
     */
    List<Product> getSimilarProducts(String productId);
}
