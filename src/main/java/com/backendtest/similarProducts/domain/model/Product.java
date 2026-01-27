package com.backendtest.similarProducts.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Product domain entity.
 * Contains the core business logic and rules for a product.
 */
public class Product {
    
    private final String id;
    private final String name;
    private final BigDecimal price;
    private final Boolean availability;

    public Product(String id, String name, BigDecimal price, Boolean availability) {
        this.id = Objects.requireNonNull(id, "Product ID cannot be null");
        this.name = Objects.requireNonNull(name, "Product name cannot be null");
        this.price = Objects.requireNonNull(price, "Product price cannot be null");
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}
