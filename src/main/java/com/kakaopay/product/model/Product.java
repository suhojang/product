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
public class Product {
	@Id
	private Long product_id;

	private String title;

	private Long total_investing_amount;

	private Long current_recruitment_amount;

	private Long investors_cnt;

	private LocalDateTime started_at;

	private LocalDateTime finished_at;

	public boolean hasId() {
		return product_id != null;
	}
}
