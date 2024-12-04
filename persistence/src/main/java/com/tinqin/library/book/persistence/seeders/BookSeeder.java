package com.tinqin.library.book.persistence.seeders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

//@Component
@RequiredArgsConstructor
//@Order(2)
public class BookSeeder implements ApplicationRunner {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    @AllArgsConstructor
    @Builder
    @Getter
    private static class BookTemplate {

        private final String title;
        private final Integer pages;
        private final BigDecimal price;
        private final BigDecimal priceRental;
        private final List<String> authors;
    }

    private final List<BookTemplate> books = List.of(

            BookTemplate
                    .builder()
                    .title("Chovekut i negovite simvoli")
                    .pages(200)
                    .price(BigDecimal.valueOf(45.0))
                    .priceRental(BigDecimal.valueOf(5.0))
                    .authors(List.of
                            (
                                    "Carl Jung",
                                    "Aniele Jaffe"
                            )
                    )
                    .build()
    );

    String BOOK_QUERY = """
            INSERT INTO books (id, created_at, is_deleted, pages, price, price_rental, stock, title, updated_on)
            VALUES (gen_random_uuid(),
                    now(),
                    false,
                    ?,
                    ?,
                    ?,
                    0,
                    ?,
                    now())
            """;

    String BOOKS_AUTHORS_QUERY = """
            INSERT INTO books_authors (book_id, author_id)
            VALUES ((SELECT id FROM books WHERE title = ?),
                   (SELECT id FROM authors WHERE first_name = ? AND last_name = ?))
            """;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Connection connection = DriverManager.getConnection(jdbcUrl, postgresUsername, postgresPassword);

        PreparedStatement ps = connection.prepareStatement(BOOK_QUERY);

        for (BookTemplate book : books) {

            ps.setInt(1, book.getPages());
            ps.setBigDecimal(2, book.getPrice());
            ps.setBigDecimal(3, book.getPriceRental());
            ps.setString(4, book.getTitle());

            ps.addBatch();
            ps.clearParameters();
        }

        ps.executeBatch();

        ps = connection.prepareStatement(BOOKS_AUTHORS_QUERY);

        for (BookTemplate book : books) {
            for (String author : book.getAuthors()) {

                ps.setString(1, book.title);

                List<String> names = List.of(author.split(" \\s*"));

                ps.setString(2, names.get(0));
                ps.setString(3, names.get(1));

                ps.addBatch();
                ps.clearParameters();
            }
        }

        ps.executeBatch();
    }
}
