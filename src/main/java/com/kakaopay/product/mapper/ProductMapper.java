package com.kakaopay.product.mapper;

import com.kakaopay.product.dto.ProductDto;
import io.r2dbc.spi.Row;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class ProductMapper implements BiFunction<Row, Object, ProductDto> {
	@Override
	public ProductDto apply(Row row, Object o) {
		return ProductDto.builder()
				.product_id(row.get("product_id", Long.class))
				.title(row.get("title", String.class))
				.total_investing_amount(row.get("total_investing_amount", Long.class))
				.current_recruitment_amount(row.get("current_recruitment_amount", Long.class))
				.investors_cnt(row.get("investors_cnt", Long.class))
				.status(row.get("status", String.class))
				.started_at(row.get("started_at", LocalDateTime.class))
				.finished_at(row.get("finished_at", LocalDateTime.class))
				.build();
	}
}
