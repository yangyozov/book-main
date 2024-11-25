package com.tinqin.library.book.core.processors.get.getbook;

import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBook;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.get.getbooks.getbook.GetBookOutput;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

    private final BookRepository bookRepository;
    private final ConversionService conversionService;

    @Override
    public GetBookOutput process(GetBookInput input) {
        Book book = fetchBook(input);
        return convertGetBookInputToGetBookOutput(book);
    }


    private Book fetchBook(GetBookInput input) {
        return bookRepository.findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    private GetBookOutput convertGetBookInputToGetBookOutput(Book book) {
        return GetBookOutput
                .builder()
                .book(conversionService.convert(book, BookModel.class))
                .build();
    }
}
