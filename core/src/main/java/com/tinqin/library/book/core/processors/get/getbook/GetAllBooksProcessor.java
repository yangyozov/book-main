package com.tinqin.library.book.core.processors.get.getbook;

import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooks;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksInput;
import com.tinqin.library.book.api.operations.get.getbooks.getallbooks.GetAllBooksOutput;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllBooksProcessor implements GetAllBooks {

    private final BookRepository bookRepository;
    private final ConversionService conversionService;

    @Override
    public GetAllBooksOutput process(GetAllBooksInput input) {
        List<Book> booksList = fetchBooks();
        return convertGetBookInputToGetBookOutput(booksList);
    }


    private List<Book> fetchBooks() {
        return bookRepository.findAll();
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
}
