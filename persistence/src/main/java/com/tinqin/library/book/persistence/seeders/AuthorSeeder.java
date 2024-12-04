package com.tinqin.library.book.persistence.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
@RequiredArgsConstructor
@Order(1)
public class AuthorSeeder implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_AUTHOR = """
            INSERT INTO authors (id, first_name, last_name)
            VALUES
            """;

    private final List<String> authors = List.of(
            "Carl Jung",
            "Nikolai Haitov",
            "John Steinbeck",
            "Aniele Jaffe");

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String names = authors.stream()
                .map(author -> author.split(" "))
                .map(authorNames -> String.format("(gen_random_uuid(), '%s', '%s')", authorNames[0], authorNames[1]))
                .collect(Collectors.joining(", "));

        String query = INSERT_AUTHOR + names;

        jdbcTemplate.execute(query);
    }
}
