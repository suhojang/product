package com.kakaopay.product.mapper;

import com.kakaopay.product.dto.UserProductDto;
import com.kakaopay.product.model.Users;
import io.r2dbc.spi.Row;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class UserProductMapper implements BiFunction<Row, Object, UserProductDto> {
	@Override
	public UserProductDto apply(Row row, Object o) {
		return UserProductDto.builder()
				.product_id(row.get("product_id", Long.class))
				.title(row.get("title", String.class))
				.total_investing_amount(row.get("total_investing_amount", Long.class))
				.my_investing_amount(row.get("my_investing_amount", Long.class))
				.investing_dt(row.get("investing_dt", LocalDateTime.class))
				.build();
	}
}
