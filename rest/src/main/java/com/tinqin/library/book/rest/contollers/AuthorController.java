package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.create.createauthor.CreateAuthorOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final CreateAuthor createAuthor;

    @PostMapping(APIRotes.API_AUTHOR)
    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorInput input) {

        CreateAuthorOutput result = createAuthor.process(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
