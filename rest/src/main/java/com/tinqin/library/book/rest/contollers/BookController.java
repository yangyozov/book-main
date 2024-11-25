package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createbook.CreateBook;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.create.createbook.CreateBookOutput;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooks;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksInput;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksOutput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBook;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookOutput;
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

    @GetMapping(APIRotes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {

        GetBookInput input = GetBookInput.
                builder().
                bookId(bookId).
                build();
        try {
            GetBookOutput result = getBook.process(input);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
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
}
