package com.kakaopay.product.controller;

import com.kakaopay.product.controller.parent.ParentControllerTests;
import com.kakaopay.product.error.ErrorResponse;
import com.kakaopay.product.model.UserProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersControllerTest extends ParentControllerTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void addMyProduct() {
		UserProduct userProduct = UserProduct.builder()
				.product_id(1L)
				.my_investing_amount(10000L)
				.build();

		webTestClient
				.post()
				.uri("/api/users/products")
				.body(BodyInserters.fromValue(userProduct))
				.accept(MediaType.APPLICATION_JSON)
				.header("X-USER-ID", "1")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody(ErrorResponse.class)
				.value((response) -> {
						assertEquals("SUCCESS", response.getResult());
						assertEquals("000", response.getErrCode());
						assertEquals("SUCCESS REQUEST", response.getErrMsg());
					}
				);
	}

	@Test
	void NotAddMyProduct() {
		addMyProduct();

		UserProduct userProduct = UserProduct.builder()
				.product_id(1L)
				.my_investing_amount(10000L)
				.build();

		webTestClient
				.post()
				.uri("/api/users/products")
				.body(BodyInserters.fromValue(userProduct))
				.accept(MediaType.APPLICATION_JSON)
				.header("X-USER-ID", "1")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody(ErrorResponse.class)
				.value((response) -> {
							assertEquals("FAIL", response.getResult());
							assertEquals("998", response.getErrCode());
							assertEquals("This product has already been registered", response.getErrMsg());
						}
				);
	}

	@Test
	void findByUserProduct() {
		addMyProduct();

		webTestClient
				.get()
				.uri("/api/users/products")
				.accept(MediaType.APPLICATION_JSON)
				.header("X-USER-ID", "1")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(List.class)
				.value((userProduct) -> {
					assertEquals(1, userProduct.size());
				});
	}
}