package com.backendtest.similarProducts.client;

import com.backendtest.similarProducts.model.ProductDetail;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ProductApiClient {

    private final RestClient restClient;

    public ProductApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<String> getSimilarProductIds(String productId) {
        try {
            return restClient.get()
                    .uri("/product/{productId}/similarids", productId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        // Return empty for 404, will be handled in service
                    })
                    .body(new ParameterizedTypeReference<List<String>>() {});
        } catch (Exception e) {
            return null; // Will trigger 404 in service
        }
    }

    public Optional<ProductDetail> getProductDetail(String productId) {
        try {
            ProductDetail product = restClient.get()
                    .uri("/product/{productId}", productId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        // Silently handle 404
                    })
                    .body(ProductDetail.class);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
