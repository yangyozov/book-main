package com.tinqin.library.book.core.processors.create;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorProcessor implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateAuthorOutput> process(CreateAuthorInput input) {

        return createAuthor(input)
                .map(savedBook -> CreateAuthorOutput
                        .builder()
                        .authorId(savedBook.getId())
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Author> createAuthor(CreateAuthorInput input) {

        return Try.of(() -> checkInputAndCreateAuthor(input));
    }

    private Author checkInputAndCreateAuthor(CreateAuthorInput input) {

        Author author = conversionService.convert(input, Author.class);

        return authorRepository.save(author);
    }
}
