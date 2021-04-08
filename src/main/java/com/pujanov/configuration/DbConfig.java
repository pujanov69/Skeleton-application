package com.pujanov.configuration;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import com.pujanov.domain.Role;
import com.pujanov.domain.User;
import com.zaxxer.hikari.HikariDataSource;
/**
 * 
 * @author Pujan KC
 *
 */
@Configuration
public class DbConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "myapp.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	TransactionAwareDataSourceProxy transactionAwareDataSourceProxy() {
		TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(primaryDataSource());
		return proxy;
	}
	
	@Bean
	PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(transactionAwareDataSourceProxy());
	}
	
	@Bean
	Jdbi jdbiBean() {
		Jdbi jdbi = Jdbi.create(transactionAwareDataSourceProxy());
		jdbi.registerRowMapper(BeanMapper.factory(User.class));
		jdbi.registerRowMapper(BeanMapper.factory(Role.class));
		jdbi.installPlugin(new SqlObjectPlugin());
		return jdbi;
	}
}
