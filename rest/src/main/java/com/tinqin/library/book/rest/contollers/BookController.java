package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createbook.CreateBook;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookOutput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookOutput;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooks;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooksInput;
import com.tinqin.library.book.api.operations.get.getallbooks.GetAllBooksOutput;
import com.tinqin.library.book.api.operations.get.getbook.GetBook;
import com.tinqin.library.book.api.operations.get.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.get.getbook.GetBookOutput;
import com.tinqin.library.book.api.operations.update.hidebook.HideBook;
import com.tinqin.library.book.api.operations.update.hidebook.HideBookInput;
import com.tinqin.library.book.api.operations.update.hidebook.HideBookOutput;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBook;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBookInput;
import com.tinqin.library.book.api.operations.update.purchasebook.PurchaseBookOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController extends BaseController{

    private final GetBook getBook;
    private final GetAllBooks getAllBooks;
    private final CreateBook createBook;
    private final DeleteBook deleteBook;
    private final HideBook hideBook;
    private final PurchaseBook purchaseBook;
    //private final LocaleHeader localeHeader;

    @GetMapping(APIRotes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {

        GetBookInput input = GetBookInput.
                builder().
                bookId(bookId).
                build();

        Either<OperationError,GetBookOutput> result = getBook.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(APIRotes.API_BOOK)
    public ResponseEntity<?> getAllBooks(@PageableDefault(page = 0, size = 2) Pageable pageable) {

        GetAllBooksInput input = GetAllBooksInput
                .builder()
                .pageable(pageable)
                .build();

        Either<OperationError,GetAllBooksOutput> result = getAllBooks.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(APIRotes.API_BOOK)
    public ResponseEntity<?> createBook(@RequestBody CreateBookInput input) {

        Either<OperationError,CreateBookOutput> result = createBook.process(input);

        return mapToResponseEntity(result, HttpStatus.CREATED);
    }

    @DeleteMapping(APIRotes.DELETE_BOOK)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {

        DeleteBookInput input = DeleteBookInput
                .builder()
                .bookId(bookId)
                .build();

        Either<OperationError,DeleteBookOutput> result = deleteBook.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping(APIRotes.GET_BOOK)
    public ResponseEntity<?> hideBook(@PathVariable("bookId") String bookId) {

        HideBookInput input = HideBookInput
                .builder()
                .bookId(bookId)
                .build();

        Either<OperationError,HideBookOutput> result = hideBook.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping(APIRotes.API_PURCHASE)
    public ResponseEntity<?> purchaseBook(@RequestBody PurchaseBookInput input) {

        Either<OperationError, PurchaseBookOutput> result = purchaseBook.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
