package com.tinqin.library.book.core.processors.get;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooks;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooksInput;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooksOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tinqin.library.book.api.operations.ValidationMessages.EMPTY_LIBRARY;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllBooksProcessor implements GetAllBooks {

    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetAllBooksOutput> process(GetAllBooksInput input) {

        return fetchBooks(input)
                .map(this::convertGetBookInputToGetBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<List<Book>> fetchBooks(GetAllBooksInput input) {

        //Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));

        Pageable pageable = input.getPageable();

        return Try.of(() -> bookRepository.findAllBooks (pageable))
                .map(this::checkFoundBooks);
    }

    private GetAllBooksOutput convertGetBookInputToGetBookOutput(List<Book> booksList) {

        return GetAllBooksOutput
                .builder()
                .books(booksList
                        .stream()
                        .map(book -> conversionService.convert(book, BookModel.class))
                        .toList()
                )
                .build();
    }

    private List<Book> checkFoundBooks(List<Book> books) {

        if (books.isEmpty()) {
            throw new BusinessException(EMPTY_LIBRARY);
        }

        return books;
    }
}
