package com.kakaopay.product.service;

import com.kakaopay.product.dto.UserProductDto;
import com.kakaopay.product.error.ErrorCode;
import com.kakaopay.product.error.ErrorResponse;
import com.kakaopay.product.model.UserProduct;
import com.kakaopay.product.repository.UserProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
	private final UserProductRepository userProductRepository;

	public Flux<UserProductDto> findByUserProducts(Long user_id){
		return userProductRepository.findByUserProducts(user_id);
	}

	public Mono<Integer> findByUserProduct(Long user_id, Long product_id){
		return userProductRepository.findByUserProduct(user_id, product_id);
	}

	@Transactional
	public Mono<ErrorResponse> saveUserProduct(Long user_id, Long product_id, Long my_investing_amount){
		UserProduct userProduct = UserProduct.builder()
				.user_id(user_id)
				.product_id(product_id)
				.my_investing_amount(my_investing_amount)
				.build();

		userProductRepository.saveUserProduct(userProduct);
		return Mono.just(ErrorResponse.of(ErrorCode.REQUEST_SUCCESS));
	}
}
