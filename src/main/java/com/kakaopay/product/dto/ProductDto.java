package com.kakaopay.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
	private Long product_id;

	private String title;

	private Long total_investing_amount;

	private Long current_recruitment_amount;

	private Long investors_cnt;

	private String status;

	private LocalDateTime started_at;

	private LocalDateTime finished_at;
}
