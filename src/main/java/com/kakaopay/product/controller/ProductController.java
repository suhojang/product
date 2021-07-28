package com.kakaopay.product.controller;

import com.kakaopay.product.dto.ProductDto;
import com.kakaopay.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
	private final ProductService productService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<ProductDto> findProductAll(@RequestHeader(name = "X-USER-ID", required = true) Long headerUserId){
		return productService.findProductAll();
	}
}
