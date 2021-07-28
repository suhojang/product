package com.kakaopay.product.repository;

import com.kakaopay.product.model.UserProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserProductRepository extends ReactiveCrudRepository<UserProduct, Long>, CustomUserProductRepository {
}
