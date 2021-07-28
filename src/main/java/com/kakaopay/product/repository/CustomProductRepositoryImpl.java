package com.kakaopay.product.repository;

import com.kakaopay.product.dto.ProductDto;
import com.kakaopay.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository{
	@Autowired
	private final DatabaseClient client;

	@Override
	public Flux<ProductDto> findProductAll() {
		String query    =
				"SELECT \n" +
						"product_id,\n" +
						"title,\n" +
						"total_investing_amount,\n" +
						"current_recruitment_amount,\n" +
						"investors_cnt,\n" +
						"case when DATEDIFF(finished_at, NOW()) < 0 then '모집완료'\n" +
						"ELSE '모집중' END as status,\n" +
						"started_at,\n" +
						"finished_at\n" +
						"FROM product\n" +
						"WHERE CURRENT_TIMESTAMP BETWEEN started_at and finished_at;";

		ProductMapper mapper = new ProductMapper();

		return client.sql(query)
				.map(mapper::apply)
				.all();
	}
}
