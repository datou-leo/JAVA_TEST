package com.example.mysqljdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;

@SpringBootApplication
public class MysqljdbctemplateApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MysqljdbctemplateApplication.class, args);
	}
	
	@Autowired
	private StudentDao studentDao;
	
	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public void run(String... args) throws Exception {
		studentDao.listData();
		ArrayList<Student> Students = new ArrayList<Student>();
		Students.add(new Student(2,"lisi"));
		Students.add(new Student(3,"wangwu"));
		studentDao.insertData(Students);
		studentDao.listData();
		studentDao.updateData(new Student(2,"lisi x"));
		studentDao.listData();
		studentDao.deleteData(new Student(3,"wangwu"));
		studentDao.listData();
	}
}
