package com.tinqin.library.book.core.processors.update;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBook;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBookInput;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tinqin.library.book.api.operations.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class PurchaseBookProcessor implements PurchaseBook {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, PurchaseBookOutput> process(PurchaseBookInput input) {

        return purchaseBook(input)
                .map(book -> PurchaseBookOutput.builder().build())
                .toEither()
                .mapLeft(errorHandler::handle);

    }

    private Try<Book> purchaseBook(PurchaseBookInput input) {

        return Try.of(() -> checkAndPurchaseBook(input));
    }

    private Book checkAndPurchaseBook(PurchaseBookInput input) {

        User user = userRepository.findById(input.getUserId()).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Book book = bookRepository.findById(input.getBookId()).orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));

        if (user.getIsBlocked()) {

            throw new BusinessException(BLOCKED_USER);
        }

        if (book.getStock() == 0) {

            throw new BusinessException(EMPTY_STOCK);
        } else {

            book.setStock(book.getStock() - 1);

            return bookRepository.save(book);
        }
    }
}
