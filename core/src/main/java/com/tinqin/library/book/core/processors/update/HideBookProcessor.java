package com.tinqin.library.book.core.processors.update;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.update.hidebook.HideBook;
import com.tinqin.library.book.api.operations.update.hidebook.HideBookInput;
import com.tinqin.library.book.api.operations.update.hidebook.HideBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HideBookProcessor implements HideBook {

    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, HideBookOutput> process(HideBookInput input) {

        return hideBook(input)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<HideBookOutput> hideBook(HideBookInput input) {

        return Try.of(() -> findBookAndHide(input));
    }

    private HideBookOutput findBookAndHide(HideBookInput input) {

        UUID idBook = UUID.fromString(input.getBookId());

        Book book = bookRepository
                .findById(idBook)
                .orElseThrow(() -> new RuntimeException(BOOK_NOT_FOUND));

        book.setIsDeleted(true);

        Book savedBook = bookRepository.save(book);

        return HideBookOutput
                .builder()
                .bookId(savedBook.getId())
                .build();
    }
}
