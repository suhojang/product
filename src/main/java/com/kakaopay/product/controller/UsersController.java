package com.kakaopay.product.controller;

import com.kakaopay.product.dto.UserProductDto;
import com.kakaopay.product.error.AlreadyRegisterException;
import com.kakaopay.product.error.ErrorResponse;
import com.kakaopay.product.error.SoldOutException;
import com.kakaopay.product.model.UserProduct;
import com.kakaopay.product.service.ProductService;
import com.kakaopay.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UsersController {
	private final UserService userService;
	private final ProductService productService;

	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<UserProductDto> findByUserProducts(@RequestHeader(name = "X-USER-ID", required = true) Long headerUserId){
		return userService.findByUserProducts(headerUserId);
	}

	@PostMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ErrorResponse> addMyProduct(@RequestHeader(name = "X-USER-ID", required = true) Long headerUserId,
	                                        @RequestBody UserProduct userProduct)  {

		return userService.findByUserProduct(headerUserId, userProduct.getProduct_id())
				.flatMap(rs -> {
					if(rs.intValue() > 0) {
						return Mono.error(new AlreadyRegisterException());
					} else {
						return productService
								.findById(userProduct.getProduct_id())
								.flatMap(
										result -> {
											if (result.getTotal_investing_amount() > result.getCurrent_recruitment_amount()) {
												return userService.saveUserProduct(
														headerUserId,
														userProduct.getProduct_id(),
														userProduct.getMy_investing_amount());
											} else {
												return Mono.error(new SoldOutException());
											}
										});
					}
				});
	}
}
