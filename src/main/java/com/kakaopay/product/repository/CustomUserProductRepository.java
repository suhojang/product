package com.kakaopay.product.repository;

import com.kakaopay.product.dto.UserProductDto;
import com.kakaopay.product.model.UserProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomUserProductRepository {
	Mono<Integer> findByUserProduct(Long user_id, Long product_id);
	void saveUserProduct(UserProduct userProduct);
	Flux<UserProductDto> findByUserProducts(Long user_id);
}
