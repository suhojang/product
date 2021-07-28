package com.kakaopay.product.repository;

import com.kakaopay.product.dto.UserProductDto;
import com.kakaopay.product.mapper.UserProductMapper;
import com.kakaopay.product.model.UserProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class CustomUserProductRepositoryImpl implements CustomUserProductRepository{
	@Autowired
	private final DatabaseClient client;

	@Override
	public Mono<Integer> findByUserProduct(Long user_id, Long product_id) {
    String query =
        "SELECT\n"
            + "COUNT(seq) AS cnt\n"
            + "FROM user_product\n"
            + "WHERE product_id = :product_id\n"
            + "AND user_id = :user_id";

		return client.sql(query)
				.bind("product_id", product_id)
				.bind("user_id", user_id)
				.fetch()
				.one().flatMap(rs -> Mono.just(Integer.parseInt(String.valueOf(rs.get("cnt")))));
	}

	@Override
	public void saveUserProduct(UserProduct userProduct) {
		String query =
				"INSERT INTO user_product\n"
						+ "(\n"
						+ "product_id\n"
						+ ",user_id\n"
						+ ",my_investing_amount\n"
						+ ")\n"
						+ "VALUES\n"
						+ "(\n"
						+ ":product_id\n"
						+ ", :user_id\n"
						+ ", :my_investing_amount\n"
						+ ")";

		client
				.sql(query)
				.bind("product_id", userProduct.getProduct_id())
				.bind("user_id", userProduct.getUser_id())
				.bind("my_investing_amount", userProduct.getMy_investing_amount())
				.fetch()
				.first()
				.log()
				.subscribe();

		query =
				"UPDATE PRODUCT\n"
						+ "SET current_recruitment_amount = current_recruitment_amount + :my_investing_amount\n"
						+ "\t,investors_cnt = investors_cnt + 1\n"
						+ "WHERE PRODUCT_ID = :product_id";

		client.sql(query)
				.bind("my_investing_amount", userProduct.getMy_investing_amount())
				.bind("product_id", userProduct.getProduct_id())
				.fetch()
				.first()
				.log()
				.subscribe();
	}

	@Override
	public Flux<UserProductDto> findByUserProducts(Long user_id) {
		String query    =
				"SELECT\n" +
						"B.product_id\n" +
						",B.title\n" +
						",B.total_investing_amount\n" +
						",A.my_investing_amount\n" +
						",A.investing_dt\n" +
						"FROM user_product A\n" +
						"INNER JOIN product B\n" +
						"ON A.product_id = B.product_id\n" +
						"WHERE A.user_id = :user_id";

		UserProductMapper mapper = new UserProductMapper();

		return client.sql(query)
				.bind("user_id", user_id)
				.map(mapper::apply)
				.all();
	}
}
