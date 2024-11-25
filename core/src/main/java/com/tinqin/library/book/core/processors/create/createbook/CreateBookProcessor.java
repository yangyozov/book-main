package com.tinqin.library.book.core.processors.create.createbook;

import com.tinqin.library.book.api.operations.create.createbook.CreateBook;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookOutput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookProcessor implements CreateBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public CreateBookOutput process(CreateBookInput input) {

        List<Author> authors = input.getAuthor()
                .stream()
                .map(authorId -> authorRepository
                        .getReferenceById(UUID.fromString(authorId))
                )
                .toList();

        Book book = Book
                .builder()
                .title(input.getTitle())
                .author(authors)
                .pages(input.getPages())
                .price(BigDecimal.valueOf(Double.parseDouble(input.getPrice())))
                .build();

        Book savedBook = bookRepository.save(book);

        return CreateBookOutput
                .builder()
                .bookId(savedBook.getId())
                .build();
    }
}
