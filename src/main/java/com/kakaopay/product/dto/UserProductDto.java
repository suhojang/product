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
public class UserProductDto {
	private Long product_id;
	private String title;
	private Long total_investing_amount;
	private Long my_investing_amount;
	private LocalDateTime investing_dt;
}
