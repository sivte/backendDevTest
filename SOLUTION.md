# Similar Products API

REST API implementation with Spring Boot 4.0.1 and Java 17 that exposes an endpoint to retrieve similar products with their details.

## Architecture

```
Client → Controller → Service → ApiClient → Mock APIs
```

Main components:

- Controller: REST endpoint GET /product/{productId}/similar
- Service: Business logic with parallel HTTP calls
- ApiClient: HTTP client with configured timeouts
- Model: DTOs (ProductDetail)
- Exception Handling: Global error handling (404)

## Evaluation criteria

### 1. Code Clarity and Maintainability

Clean and organized architecture:

- Separation of concerns (Controller, Service, Client, Config)
- Constructor-based dependency injection
- Descriptive names and self-documenting code
- Centralized exception handling with @RestControllerAdvice

### 2. Performance

Implemented optimizations:

Parallel HTTP calls:

- Using CompletableFuture with ForkJoinPool of 50 threads
- Instead of N sequential calls, all are executed in parallel
- 25-40% improvement in scenarios with multiple similar products
- 3x improvement in high-latency scenarios (1-5 second delays)

Configured timeouts:

- Connect timeout: 2 seconds
- Read timeout: 5 seconds
- Prevents indefinite blocking

K6 test results (200 concurrent VUs):

- 116 requests/second
- P90: 149ms
- P95: 378ms

### 3. Resilience

Implemented strategies:

- Timeouts: Prevents infinite waits on HTTP calls
- Robust error handling:
  - Returns 404 when main product does not exist
  - Filters similar products that cannot be obtained without breaking the flow
- Robust HTTP client: Spring RestClient with error handling
- Controlled thread pool: 50 thread limit for I/O operations

## Project structure

```
src/main/java/com/backendtest/similarProducts/
├── SimilarProductsApplication.java
├── client/
│   └── ProductApiClient.java
├── config/
│   └── RestClientConfig.java
├── controller/
│   └── SimilarProductsController.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ProductNotFoundException.java
├── model/
│   └── ProductDetail.java
└── service/
    └── SimilarProductsService.java
```

## Execution

Option 1 - Docker Compose (recommended):

```bash
docker-compose up -d --build
docker-compose run --rm k6 run scripts/test.js
```

View results: http://localhost:3000/d/Le2Ku9NMk/k6-performance-test

Option 2 - Local:

```bash
docker-compose up -d simulado influxdb grafana
./mvnw spring-boot:run
curl http://localhost:8080/product/1/similar
```

## API

GET /product/{productId}/similar

Returns the details of similar products for a given product.

Successful response (200):

```json
[
  {
    "id": "2",
    "name": "Product 2",
    "price": 29.99,
    "availability": true
  }
]
```

Product not found (404):

```
HTTP/1.1 404 Not Found
```

## Technologies

- Spring Boot 4.0.1
- Java 17
- RestClient (Spring 6.x)
- Maven 3.9.6
- Docker and Docker Compose
- K6 for load testing
- Grafana and InfluxDB for metrics

## Technical notes

Port 8080: Port 5000 is occupied by AirPlay Receiver on macOS Monterey and later versions.

Parallelization: HTTP calls to similar products are executed in parallel using CompletableFuture with ForkJoinPool, optimized for I/O operations.

Error handling: If the main product does not exist, returns 404. If any similar product cannot be obtained, it is filtered from the response without breaking the flow.
