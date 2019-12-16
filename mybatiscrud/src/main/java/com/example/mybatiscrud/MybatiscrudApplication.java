package com.example.mybatiscrud;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@MapperScan("com.example.mybatiscrud")
@SpringBootApplication
public class MybatiscrudApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatiscrudApplication.class, args);
	}

	@Autowired
	private IFoodMapper ifood;

	@Override
	public void run(String... args) throws Exception {

		List<Food> Foods = (List<Food>)ifood.findAll();
		Foods.forEach(Food->log.info("Food:{}",Food));



		ifood.insertByFood(new Food(1,"Food1"));
		ifood.insertByFood(new Food(2,"Food2"));
		ifood.insertByFood(new Food(3,"Food3"));

		List<Food> list = (List<Food>)ifood.findAll();
		list.forEach(Food->log.info("Food:{}",Food));

		Food ob =ifood.findById(2);

		ob.setName("Food two");
		ifood.update(ob);


		List<Food> lis = (List<Food>)ifood.findAll();
		lis.forEach(Food->log.info("Food:{}",Food));

		ifood.delete(3);

		List<Food> ls = (List<Food>)ifood.findAll();
		ls.forEach(Food->log.info("Food:{}",Food));

	}
}
