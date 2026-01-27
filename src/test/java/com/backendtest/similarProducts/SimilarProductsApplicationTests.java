package com.backendtest.similarProducts;

import com.backendtest.BackendTestApplication;
import com.backendtest.similarProducts.domain.port.in.GetSimilarProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BackendTestApplication.class)
class SimilarProductsApplicationTests {

	@Autowired
	private GetSimilarProductsUseCase getSimilarProductsUseCase;

	@Test
	void contextLoads() {
		assertThat(getSimilarProductsUseCase).isNotNull();
	}

}
