package com.tinqin.library.book.core.processors.get.getbook;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBook;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.BOOK_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError,GetBookOutput> process(GetBookInput input) {

        return fetchBook(input)
                .map(this::convertGetBookInputToGetBookOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<Book> fetchBook(GetBookInput input) {
        return Try.of(()->bookRepository.findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND)));
    }

    private GetBookOutput convertGetBookInputToGetBookOutput(Book book) {
        return GetBookOutput
                .builder()
                .book(conversionService.convert(book, BookModel.class))
                .build();
    }
}
