package com.backendtest.similarProducts.service;

import com.backendtest.similarProducts.client.ProductApiClient;
import com.backendtest.similarProducts.exception.ProductNotFoundException;
import com.backendtest.similarProducts.model.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

@Service
public class SimilarProductsService {

    private final ProductApiClient productApiClient;
    private final ForkJoinPool executorService;

    public SimilarProductsService(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
        // Use common pool with parallelism configured for I/O operations
        this.executorService = new ForkJoinPool(50);
    }

    public List<ProductDetail> getSimilarProducts(String productId) {
        List<String> similarIds = productApiClient.getSimilarProductIds(productId);
        
        if (similarIds == null) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }

        // Fetch all products in PARALLEL for better performance
        List<CompletableFuture<ProductDetail>> futures = similarIds.stream()
                .map(id -> CompletableFuture.supplyAsync(
                        () -> productApiClient.getProductDetail(id).orElse(null),
                        executorService))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(product -> product != null)
                .toList();
    }
}
