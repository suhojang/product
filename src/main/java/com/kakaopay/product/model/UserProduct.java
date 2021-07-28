package com.kakaopay.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserProduct {
	@Id
	private Long seq;

	private Long user_id;

	private Long product_id;

	private Long my_investing_amount;

	private LocalDateTime investing_dt;

	private Product product;

	public UserProduct(Long seq, Long my_investing_amount, LocalDateTime investing_dt, Product product){
		this.seq = seq;
		this.my_investing_amount = my_investing_amount;
		this.investing_dt = investing_dt;
		this.product = product;
	}
}
