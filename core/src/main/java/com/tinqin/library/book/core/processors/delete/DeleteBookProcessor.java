package com.tinqin.library.book.core.processors.delete;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.BOOK_NOT_FOUND;
import static com.tinqin.library.book.api.operations.ValidationMessages.EMPTY_LIBRARY;

@Service
@RequiredArgsConstructor
public class DeleteBookProcessor implements DeleteBook {

    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, DeleteBookOutput> process(DeleteBookInput input) {

        return deleteBook(input)
                .toEither()
                .mapLeft(errorHandler::handle);

    }

    private Try<DeleteBookOutput> deleteBook(DeleteBookInput input) {

        return Try.of(() -> findBookAndDelete(input));
    }

    private DeleteBookOutput findBookAndDelete(DeleteBookInput input) {

        UUID idBook = UUID.fromString(input.getBookId());

        Book book = bookRepository
                .findById(idBook)
                .orElseThrow(() ->new BusinessException(BOOK_NOT_FOUND));

        bookRepository.delete(book);

        return DeleteBookOutput
                .builder()
                .bookId(idBook)
                .build();
    }
}
