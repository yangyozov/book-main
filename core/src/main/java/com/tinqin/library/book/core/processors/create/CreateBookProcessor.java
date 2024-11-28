package com.tinqin.library.book.core.processors.create;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.create.createbook.CreateBook;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class CreateBookProcessor implements CreateBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateBookOutput> process(CreateBookInput input) {

        return createBook(input)
                .map(savedBook -> CreateBookOutput
                        .builder()
                        .bookId(savedBook.getId())
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Book> createBook(CreateBookInput input) {

        return Try.of(() -> checkInputAndCreateBook(input));
    }

    private Book checkInputAndCreateBook(CreateBookInput input) {

        List<UUID> authorsInputIdList = input.getAuthor()
                .stream()
                .map(UUID::fromString)
                .toList();

        List<Author> authors = authorRepository.findAuthorsById(authorsInputIdList);

        if (authors.isEmpty()) {
            throw new BusinessException(AUTHORS_NOT_FOUND);
        }

        if (authorsInputIdList.size() != authors.size()) {

            List<UUID> authorsFoundIdList = authors
                    .stream()
                    .map(Author::getId)
                    .toList();

            List<UUID> comparedIdList = authorsInputIdList
                    .stream()
                    .filter(id -> !authorsFoundIdList.contains(id))
                    .toList();

            String IdAuthorsString = comparedIdList.toString();

            throw new BusinessException(AUTHORS_ID_NOT_FOUND + IdAuthorsString);
        }

        Book book = Book
                .builder()
                .title(input.getTitle())
                .author(authors)
                .pages(input.getPages())
                .price(BigDecimal.valueOf(Double.parseDouble(input.getPrice())))
                .build();

        return bookRepository.save(book);
    }
}
