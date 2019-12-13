package com.example.jdbctemplate;

import org.omg.CORBA.IDLTypeOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class JdbctemplateApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JdbctemplateApplication.class, args);
	}



	@Autowired
	private PeopleDao peopleDao;

	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void run(String... args) throws Exception {
		peopleDao.listData();
		ArrayList<People> peoples = new ArrayList<People>();
		peoples.add(new People(2,"lisi"));
		peoples.add(new People(3,"wangwu"));
		peopleDao.insertData(peoples);
		peopleDao.listData();
		peopleDao.updateData(new People(2,"lisi x"));
		peopleDao.listData();
		peopleDao.deleteData(new People(3,"wangwu"));
		peopleDao.listData();

	}
}
