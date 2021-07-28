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
public class Users {
	@Id
	private Long user_id;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;

	private UserProduct userProduct;
}
