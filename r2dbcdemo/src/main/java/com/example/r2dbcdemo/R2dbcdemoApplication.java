package com.example.r2dbcdemo;

import com.example.r2dbcdemo.converter.MoneyReadConverter;
import com.example.r2dbcdemo.converter.MoneyWriteConverter;
import com.example.r2dbcdemo.model.Coffee;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
public class R2dbcdemoApplication extends AbstractR2dbcConfiguration implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(R2dbcdemoApplication.class, args);
	}
	@Autowired
	private DatabaseClient client;

	@Bean
	public ConnectionFactory connectionFactory(){
		return new H2ConnectionFactory(H2ConnectionConfiguration.builder().inMemory("testdb").username("sa").build());
	}

	@Bean
	public R2dbcCustomConversions r2dbcCustomConversions(){
		Dialect dialect=getDialect(connectionFactory());
		CustomConversions.StoreConversions storeConversions=CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
		return new R2dbcCustomConversions(storeConversions, Arrays.asList(new MoneyReadConverter(),new MoneyWriteConverter()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		CountDownLatch cdl=new CountDownLatch(2);

		client.execute().sql("select * from t_coffee")
				.as(Coffee.class)
				.fetch()
				.first()
				.doFinally(s->cdl.countDown())
				//			.subscribeOn(Schedulers.elastic())
				.subscribe(c->log.info("Fetch execute(){}",c));


		client.select()
				.from("t_coffee")
				.orderBy(Sort.by(Sort.Direction.DESC,"id"))
				.page(PageRequest.of(0,3))
				.as(Coffee.class)
				.fetch()
				.all()
				.doFinally(s-> cdl.countDown())
				//			.subscribeOn(Schedulers.elastic())
				.subscribe(c->log.info("Fetch select()",c));

		log.info("After Starting");
		cdl.await();
	}
}
