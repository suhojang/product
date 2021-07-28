package com.kakaopay.product.controller.parent;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ParentControllerTests {
	@Autowired
	ConnectionFactory cf;

	@BeforeEach
	void setUp() {
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

		Flux.from(cf.create())
				.flatMap(c ->
						c.createBatch()
								.add("DROP TABLE IF EXISTS USER_PRODUCT;")
								.add("DROP TABLE IF EXISTS PRODUCT;")
								.add("DROP TABLE IF EXISTS USERS;")
								.add(create_product)
								.add(create_users)
								.add(create_user_product)
								.add(insert_users)
								.add(insert_product01)
								.add(insert_product02)
								.execute()
				)
				.log()
				.blockLast();
	}

}
