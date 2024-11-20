package com.tinqin.library.book.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.tinqin.library.book")
@EntityScan("com.tinqin.library.book.persistence.models")
//@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.tinqin.library.book.persistence.repositories")
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
