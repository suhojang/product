package com.kakaopay.product.service;

import com.kakaopay.product.dto.ProductDto;
import com.kakaopay.product.model.Product;
import com.kakaopay.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Flux<ProductDto> findProductAll(){
		return productRepository.findProductAll();
	}

	public Mono<Product> findById(Long product_id) {
		return productRepository.findById(product_id);
	}
}
