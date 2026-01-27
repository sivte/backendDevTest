package com.backendtest.similarProducts.application.service;

import com.backendtest.similarProducts.domain.exception.ProductNotFoundException;
import com.backendtest.similarProducts.domain.model.Product;
import com.backendtest.similarProducts.domain.port.in.GetSimilarProductsUseCase;
import com.backendtest.similarProducts.domain.port.out.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * Application service that implements the GetSimilarProducts use case.
 * business logic
 */
@Service
public class GetSimilarProductsService implements GetSimilarProductsUseCase {

    private final ProductRepository productRepository;
    private final ForkJoinPool executorService;

    public GetSimilarProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.executorService = new ForkJoinPool(50);
    }

    @Override
    public List<Product> getSimilarProducts(String productId) {
        // Get similar product IDs
        List<String> similarIds = productRepository.getSimilarProductIds(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Fetch all product in parallel
        List<CompletableFuture<Product>> futures = similarIds.stream()
                .map(id -> CompletableFuture.supplyAsync(
                        () -> productRepository.getProductById(id).orElse(null),
                        executorService))
                .toList();

        // Wait for all and filter
        return futures.stream()
                .map(CompletableFuture::join)
                .filter(product -> product != null)
                .toList();
    }
}
