package com.kakaopay.product.controller;

import com.kakaopay.product.controller.parent.ParentControllerTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTest extends ParentControllerTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void findProductAll() {
		webTestClient
				.get()
				.uri("/api/products")
				.accept(MediaType.APPLICATION_JSON)
				.header("X-USER-ID", "1")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(List.class)
				.value((product) -> {
					assertEquals(2, product.size());
				});
	}
}