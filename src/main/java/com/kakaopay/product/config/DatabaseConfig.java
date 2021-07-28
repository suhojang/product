package com.kakaopay.product.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class DatabaseConfig extends AbstractR2dbcConfiguration {

	@Override
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(
				ConnectionFactoryOptions.builder()
					.option(DRIVER, "mysql")
					.option(HOST, "127.0.0.1")
					.option(USER, "root")
					.option(PASSWORD, "kwic5539")
					.option(DATABASE, "testdb")
					.build());
	}

	@Bean
	public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}

	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);

		CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
		populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
		initializer.setDatabasePopulator(populator);

		return initializer;
	}
}
