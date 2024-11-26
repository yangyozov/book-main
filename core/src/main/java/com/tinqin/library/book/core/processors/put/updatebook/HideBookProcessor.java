package com.tinqin.library.book.core.processors.put.updatebook;

import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBook;
import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBookInput;
import com.tinqin.library.book.api.operations.put.updatebook.hidebook.HideBookOutput;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HideBookProcessor implements HideBook {

    private final BookRepository bookRepository;

    @Override
    public HideBookOutput process(HideBookInput input) {

        UUID idBook = UUID.fromString(input.getBookId());

        Book book = bookRepository
                .findById(idBook)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setIsDeleted(true);

        Book savedBook = bookRepository.save(book);

        return HideBookOutput
                .builder()
                .bookId(idBook)
                .build();
    }
}
