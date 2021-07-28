package com.kakaopay.product.repository;

import com.kakaopay.product.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long>, CustomProductRepository {
}
