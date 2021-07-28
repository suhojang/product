package com.kakaopay.product.repository;

import com.kakaopay.product.model.Product;
import com.kakaopay.product.model.UserProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class UserProductRepositoryTest {
	@Autowired
	UserProductRepository userProductRepository;

	@Autowired
	DatabaseClient database;

	@BeforeEach
	void setUp() {
		Hooks.onOperatorDebug();

		String create_product = "CREATE TABLE IF NOT EXISTS PRODUCT (\n" +
				"\tproduct_id INT\tUNSIGNED AUTO_INCREMENT NOT NULL\n" +
				"\t,title VARCHAR(100) NOT NULL\n" +
				"\t,total_investing_amount int NOT NULL DEFAULT 0\n" +
				"\t,current_recruitment_amount int NOT NULL DEFAULT 0\n" +
				"\t,investors_cnt int NOT NULL DEFAULT 0\n" +
				"\t,started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
				"\t,finished_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
				"\n" +
				"\t,PRIMARY KEY (product_id)\n" +
				");";

		String create_users = "CREATE TABLE IF NOT EXISTS USERS (\n"
				+ "\tuser_id INT\tUNSIGNED AUTO_INCREMENT NOT NULL\n"
				+ "\t,created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n"
				+ "\t,updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n"
				+ "\n"
				+ "\t,PRIMARY KEY (user_id)\n"
				+ ");";

		String create_user_product = "CREATE TABLE IF NOT EXISTS USER_PRODUCT (\n"
				+ "   seq INT UNSIGNED AUTO_INCREMENT NOT NULL\n"
				+ "\t,product_id\tINT UNSIGNED\tNOT NULL\n"
				+ "\t,user_id\tINT UNSIGNED\tNOT NULL\n"
				+ "\t,my_investing_amount\tINT NOT NULL\n"
				+ "\t,investing_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n"
				+ "\n"
				+ "\t,PRIMARY KEY (seq)\n"
				+ "    ,CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(product_id) on update cascade on delete cascade\n"
				+ "\t,CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) on update cascade on delete cascade\n"
				+ ");";

		String insert_users = "INSERT INTO users (created_at, updated_at) VALUES (NOW(), NOW());";
		String insert_product01 = "INSERT INTO PRODUCT (title, total_investing_amount, started_at, finished_at) VALUES ('제약회사 보험상품',1000000, NOW(), date_add(now(),INTERVAL 3 DAY));";
		String insert_product02 = "INSERT INTO PRODUCT (title, total_investing_amount, started_at, finished_at) VALUES ('독서실 보험상품',3000000, NOW(), date_add(now(),INTERVAL 3 DAY));";

		List<String> statements = Arrays.asList(
				"DROP TABLE IF EXISTS USER_PRODUCT;",
				"DROP TABLE IF EXISTS PRODUCT;",
				"DROP TABLE IF EXISTS USERS;",
				create_product,
				create_users,
				create_user_product,
				insert_users,
				insert_product01,
				insert_product02
		);

		statements.forEach(it -> database.sql(it)
				.fetch()
				.rowsUpdated()
				.as(StepVerifier::create)
				.expectNextCount(1)
				.verifyComplete());
	}

	@Test
	public void findByUserProduct(){
		Long user_id = 1L;
		Long product_id = 1L;

		UserProduct sample1 = UserProduct.builder()
				.product_id(product_id)
				.user_id(user_id)
				.my_investing_amount(10000L)
				.build();

		insertCustomers(sample1);

		userProductRepository.findByUserProduct(user_id, product_id)
				.as(StepVerifier::create)
				.assertNext(Integer::intValue)
				.verifyComplete();
	}

	@Test
	public void findByUserProducts(){
		Long user_id = 1L;
		UserProduct sample1 = UserProduct.builder()
				.product_id(1L)
				.user_id(user_id)
				.my_investing_amount(10000L)
				.build();

		UserProduct sample2 = UserProduct.builder()
				.product_id(2L)
				.user_id(user_id)
				.my_investing_amount(20000L)
				.build();

		insertCustomers(sample1, sample2);

		userProductRepository.findByUserProducts(user_id)
				.as(StepVerifier::create)
				.assertNext(sample1::equals)
				.assertNext(sample2::equals)
				.verifyComplete();
	}

	private void saveUserProduct(UserProduct userProducts){
		this.userProductRepository.saveUserProduct(userProducts);
	}

	private void insertCustomers(UserProduct... userProducts) {
		/*this.userProductRepository.saveAll(Arrays.asList(userProducts))
				.as(StepVerifier::create)
				.expectNextCount(userProducts.length)
				.verifyComplete();
*/
		Arrays.asList(userProducts).stream().parallel().forEach(userProduct -> {
			this.userProductRepository.saveUserProduct(userProduct);
		});
	}
}