# API de Productos Similares

Implementación de API REST con Spring Boot 4.0.1 y Java 17 que expone un endpoint para obtener productos similares con sus detalles.

## Arquitectura

```
Cliente → Controller → Service → ApiClient → APIs Mock
```

Componentes principales:

- Controller: Endpoint REST GET /product/{productId}/similar
- Service: Lógica de negocio con llamadas HTTP paralelas
- ApiClient: Cliente HTTP con timeouts configurados
- Model: DTOs (ProductDetail)
- Exception Handling: Manejo global de errores (404)

## Evaluación según criterios

### 1. Claridad y Mantenibilidad del Código

Arquitectura limpia y organizada:

- Separación de responsabilidades (Controller, Service, Client, Config)
- Inyección de dependencias con constructor
- Nombres descriptivos y código autodocumentado
- Manejo centralizado de excepciones con @RestControllerAdvice

### 2. Rendimiento

Optimizaciones implementadas:

Llamadas HTTP paralelas:

- Uso de CompletableFuture con ForkJoinPool de 50 threads
- En lugar de hacer N llamadas secuenciales, se hacen todas en paralelo
- Mejora de 25-40% en escenarios con múltiples productos similares
- Mejora de 3x en escenarios con alta latencia (delays de 1-5 segundos)

Timeouts configurados:

- Connect timeout: 2 segundos
- Read timeout: 5 segundos
- Evita bloqueos indefinidos

Resultados de pruebas K6 (200 VUs concurrentes):

- 116 requests/segundo
- P90: 149ms
- P95: 378ms

### 3. Resiliencia

Estrategias implementadas:

- Timeouts: Evita esperas infinitas en llamadas HTTP
- Manejo de errores robusto:
  - Retorna 404 cuando el producto principal no existe
  - Filtra productos similares que no se pueden obtener sin romper el flujo
- HTTP client robusto: RestClient de Spring con manejo de errores
- Pool de threads controlado: Límite de 50 threads para operaciones I/O

## Estructura del proyecto

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

## Ejecución

Opción 1 - Docker Compose (recomendado):

```bash
docker-compose up -d --build
docker-compose run --rm k6 run scripts/test.js
```

Ver resultados: http://localhost:3000/d/Le2Ku9NMk/k6-performance-test

Opción 2 - Local:

```bash
docker-compose up -d simulado influxdb grafana
./mvnw spring-boot:run
curl http://localhost:8080/product/1/similar
```

## API

GET /product/{productId}/similar

Retorna los detalles de los productos similares a uno dado.

Respuesta exitosa (200):

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

Producto no encontrado (404):

```
HTTP/1.1 404 Not Found
```

## Tecnologías

- Spring Boot 4.0.1
- Java 17
- RestClient (Spring 6.x)
- Maven 3.9.6
- Docker y Docker Compose
- K6 para load testing
- Grafana e InfluxDB para métricas

## Notas técnicas

Puerto 8080: El puerto 5000 está ocupado por AirPlay Receiver en macOS Monterey y versiones posteriores.

Paralelización: Las llamadas HTTP a los productos similares se ejecutan en paralelo usando CompletableFuture con ForkJoinPool, optimizado para operaciones I/O.

Manejo de errores: Si el producto principal no existe retorna 404. Si algún producto similar no se puede obtener se filtra de la respuesta sin romper el flujo.
