package com.example.resttemplateplusdemo;

import com.example.resttemplateplusdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ResttemplateplusdemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ResttemplateplusdemoApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE).run(args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}


	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		URI uri= UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/?name={name}").build("mocha");

		RequestEntity<Void> req=RequestEntity.get(uri).accept(MediaType.APPLICATION_XML).build();

		ResponseEntity<String> resp=restTemplate.exchange(req,String.class);

		log.info("Response status:{}",resp.getStatusCode(),resp.getHeaders());
		log.info("Coffee:{}",resp.getBody());

		String coffeeUri="http://localhost:8080/coffee/";

		Coffee response= Coffee.builder().name("摩卡").price(Money.of(CurrencyUnit.of("CNY"),25.00)).build();
		log.info("New Coffee:{}",response);

		ParameterizedTypeReference<List<Coffee>> parameterizedTypeReference=new ParameterizedTypeReference<List<Coffee>>() {};

		ResponseEntity<List<Coffee>>  list=restTemplate.exchange(coffeeUri, HttpMethod.GET,null,parameterizedTypeReference);

		list.getBody().forEach(c->log.info("Coffee: {}:",c));


	}
}
