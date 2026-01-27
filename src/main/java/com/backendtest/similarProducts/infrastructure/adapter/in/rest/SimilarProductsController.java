package com.backendtest.similarProducts.infrastructure.adapter.in.rest;

import com.backendtest.similarProducts.domain.model.Product;
import com.backendtest.similarProducts.domain.port.in.GetSimilarProductsUseCase;
import com.backendtest.similarProducts.infrastructure.adapter.in.rest.dto.ProductResponse;
import com.backendtest.similarProducts.infrastructure.adapter.in.rest.mapper.ProductResponseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST adapter (input adapter) that exposes the similar products endpoint.
 */
@RestController
@RequestMapping("/product")
public class SimilarProductsController {

    private final GetSimilarProductsUseCase getSimilarProductsUseCase;
    private final ProductResponseMapper mapper;

    public SimilarProductsController(
            GetSimilarProductsUseCase getSimilarProductsUseCase,
            ProductResponseMapper mapper) {
        this.getSimilarProductsUseCase = getSimilarProductsUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{productId}/similar")
    public List<ProductResponse> getSimilarProducts(@PathVariable String productId) {
        List<Product> products = getSimilarProductsUseCase.getSimilarProducts(productId);
        return products.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
