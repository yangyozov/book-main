package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createbook.CreateBook;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookOutput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookOutput;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooks;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksInput;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksOutput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBook;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookOutput;
import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBook;
import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBookInput;
import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBookOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final GetBook getBook;
    private final GetAllBooks getAllBooks;
    private final CreateBook createBook;
    private final DeleteBook deleteBook;
    private final HideBook hideBook;

    @GetMapping(APIRotes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {

        GetBookInput input = GetBookInput.
                builder().
                bookId(bookId).
                build();

        GetBookOutput result = getBook.process(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(APIRotes.API_BOOK)
    public ResponseEntity<?> getAllBooks() {

        GetAllBooksInput input = GetAllBooksInput.
                builder().
                build();

        GetAllBooksOutput result = getAllBooks.process(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(APIRotes.API_BOOK)
    public ResponseEntity<?> createBook(@RequestBody CreateBookInput input) {

        CreateBookOutput result = createBook.process(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(APIRotes.DELETE_BOOK)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {

        DeleteBookInput input = DeleteBookInput
                .builder()
                .bookId(bookId)
                .build();

        DeleteBookOutput result = deleteBook.process(input);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(APIRotes.GET_BOOK)
    public ResponseEntity<?> hideBook(@PathVariable("bookId") String bookId) {

        HideBookInput input = HideBookInput
                .builder()
                .bookId(bookId)
                .build();

        HideBookOutput result = hideBook.process(input);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
