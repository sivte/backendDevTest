package com.backendtest.similarProducts.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

/**
 * REST API response DTO for product details.
 */
public class ProductResponse {
    
    private String id;
    private String name;
    private BigDecimal price;
    private Boolean availability;

    public ProductResponse() {
    }

    public ProductResponse(String id, String name, BigDecimal price, Boolean availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
