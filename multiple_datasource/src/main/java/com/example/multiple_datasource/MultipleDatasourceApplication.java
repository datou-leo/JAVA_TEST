package com.example.multiple_datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
public class MultipleDatasourceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatasourceApplication.class, args);
	}


	@Bean
	@ConfigurationProperties("foo.datasource")
	public DataSourceProperties fooDataSourceProperties(){
		return new DataSourceProperties();
	}

	@Bean
	public DataSource fooDataSource(){
		DataSourceProperties dataSourceProperties = fooDataSourceProperties();
		log.info("foo datasource:{}");
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Resource
	public PlatformTransactionManager fooTxManager(DataSource fooDataSource){
		return new DataSourceTransactionManager(fooDataSource);
	}


	@Bean
	@ConfigurationProperties("bar.datasource")
	public DataSourceProperties barDataSourceProperties(){
		return new DataSourceProperties();
	}

	@Bean
	public DataSource barDataSource(){
		DataSourceProperties dataSourceProperties = barDataSourceProperties();
		log.info("bar datasource:{}");
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	@Resource
	public PlatformTransactionManager barTxManager(DataSource barDataSource){
		return new DataSourceTransactionManager(barDataSource);
	}

	/*
	 * 初始化foo数据库脚本代码
	 */
	@Bean
	public DataSourceInitializer fooDataSourceInitializer(@Qualifier("fooDataSource") DataSource dataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("foo-schema.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("foo-data.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}


	/*
	 * 初始化bar数据库脚本代码
	 */
	@Bean
	public DataSourceInitializer barDataSourceInitializer(@Qualifier("barDataSource") DataSource dataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("bar-schema.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("bar-data.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}



	@Override
	public void run(String... args) throws Exception {
		showFooConnectionData();
		showBarConnectionData();
	}
	/*
	 * 展示foo数据库脚本代码
	 */
	private void showFooConnectionData() throws SQLException {
		DataSource dataSource = fooDataSource();
		log.info(dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info(conn.toString());
		//想要获取一个jdbcTemplate对象，只需要获取一个dataSource
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.queryForList("SELECT * FROM FOO").forEach(row->log.info(row.toString()));
		conn.close();
	}
	/*
	 * 展示bar数据库脚本代码
	 */
	private void showBarConnectionData() throws SQLException {
		DataSource dataSource = barDataSource();
		log.info(dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info(conn.toString());
		//想要获取一个jdbcTemplate对象，只需要获取一个dataSource
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.queryForList("SELECT * FROM BAR").forEach(row->log.info(row.toString()));
		conn.close();
	}



}
