package com.tinqin.library.book.core.processors.create;

import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorOutput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorProcessor implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    @Override
    public CreateAuthorOutput process(CreateAuthorInput input) {

        Author author = conversionService.convert(input, Author.class);

        Author savedBook = authorRepository.save(author);

        return CreateAuthorOutput
                .builder()
                .authorId(savedBook.getId())
                .build();
    }
}
