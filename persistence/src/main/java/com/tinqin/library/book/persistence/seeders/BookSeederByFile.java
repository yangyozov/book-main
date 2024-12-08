package com.tinqin.library.book.persistence.seeders;

import com.tinqin.library.book.persistence.filereaderfactory.FileReaderFactory;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

//@Component
@RequiredArgsConstructor
@Order(2)
public class BookSeederByFile implements ApplicationRunner {

    private final FileReaderFactory fileReaderFactory;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    String BOOK_QUERY = """
            INSERT INTO books (id, created_at, is_deleted, pages, price, price_rental, stock, title, updated_on)
            VALUES (gen_random_uuid(),
                    now(),
                    false,
                    ?,
                    ?,
                    ?,
                    ?,
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

        //FileReader fileReader = FileReader.loadFile("books.csv", 2);

        //FileReader fileReader = fileReaderFactory.createCsvFileReader("books.csv", 2);

        FileReader fileReader = fileReaderFactory.createJsonFileReader("books.json", 2);

        List<BookModel> batch = fileReader.getBatch();

        while (!batch.isEmpty()) {

            PreparedStatement ps = connection.prepareStatement(BOOK_QUERY);

            for (BookModel book : batch) {

                ps.setInt(1, book.getPages());
                ps.setBigDecimal(2, book.getPrice());
                ps.setBigDecimal(3, book.getPriceRental());
                ps.setInt(4, book.getStock());
                ps.setString(5, book.getTitle());

                ps.addBatch();
                ps.clearParameters();
            }

            ps.executeBatch();

            ps = connection.prepareStatement(BOOKS_AUTHORS_QUERY);

            for (BookModel book : batch) {
                for (String author : book.getAuthors()) {

                    ps.setString(1, book.getTitle());

                    List<String> names = List.of(author.split(" \\s*"));

                    ps.setString(2, names.get(0));
                    ps.setString(3, names.get(1));

                    ps.addBatch();
                    ps.clearParameters();
                }
            }

            ps.executeBatch();

            batch = fileReader.getBatch();
        }
    }
}
