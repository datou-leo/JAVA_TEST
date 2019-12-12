package com.example.jpacrud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class JpacrudApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JpacrudApplication.class, args);
	}

	@Autowired
	private BookRepository br;


	@Override
	public void run(String... args) throws Exception {

		List<Book> books = (List<Book>)br.findAll();
		books.forEach(book->log.info("BOOK:{}",book));



		br.save(new Book(1,"book1"));
		br.save(new Book(2,"book2"));
		br.save(new Book(3,"book3"));

		List<Book> list = (List<Book>)br.findAll();
		list.forEach(book->log.info("BOOK:{}",book));

		Optional<Book> ob =br.findById(2);

		Book b=new Book();
		if(ob.isPresent()){
			b = ob.get();
			b.setName("book two");
			br.save(b);
		}


		List<Book> lis = (List<Book>)br.findAll();
		lis.forEach(book->log.info("BOOK:{}",book));

		br.deleteById(3);

		List<Book> ls = (List<Book>)br.findAll();
		ls.forEach(book->log.info("BOOK:{}",book));
	}

}
