package com.backendtest.similarProducts.domain.exception;

/**
 * Domain exception thrown when a product is not found.
 */
public class ProductNotFoundException extends RuntimeException {
    
    private final String productId;

    public ProductNotFoundException(String productId) {
        super("Product not found: " + productId);
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
