package com.tinqin.library.book.core.processors.delete;

import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.delete.deletebook.DeleteBookOutput;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBookProcessor implements DeleteBook {

    private final BookRepository bookRepository;

    @Override
    public DeleteBookOutput process(DeleteBookInput input) {

        UUID idBook = UUID.fromString(input.getBookId());

        Book book = bookRepository
                .findById(idBook)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);

        return DeleteBookOutput
                .builder()
                .bookId(idBook)
                .build();
    }
}
