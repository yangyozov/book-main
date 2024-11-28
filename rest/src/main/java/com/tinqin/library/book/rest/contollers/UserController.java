package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createusers.CreateUser;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserInput;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserOutput;
import com.tinqin.library.book.api.operations.put.updateuser.BlockUser;
import com.tinqin.library.book.api.operations.put.updateuser.BlockUserInput;
import com.tinqin.library.book.api.operations.put.updateuser.BlockUserOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController extends BaseController {

    private final CreateUser createUser;
    private final BlockUser blockUser;

    @PostMapping(APIRotes.API_USER)
    public ResponseEntity<?> createUser(@RequestBody CreateUserInput input) {

        Either<OperationError, CreateUserOutput> result = createUser.process(input);

        return mapToResponseEntity(result, HttpStatus.CREATED);
    }

    @PostMapping(APIRotes.BLOCK_USER)
    public ResponseEntity<?> blockUser(@PathVariable("userId") String userId) {

        BlockUserInput input = BlockUserInput
                .builder()
                .userId(userId)
                .build();

        Either<OperationError, BlockUserOutput> result = blockUser.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
