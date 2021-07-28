package com.kakaopay.product.repository;

import com.kakaopay.product.dto.ProductDto;
import reactor.core.publisher.Flux;

public interface CustomProductRepository {
	Flux<ProductDto> findProductAll();
}
