package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createusers.CreateUser;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserInput;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserOutput;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUser;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUserInput;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUserOutput;
import com.tinqin.library.book.api.operations.update.unblockuser.UnBlockUser;
import com.tinqin.library.book.api.operations.update.unblockuser.UnBlockUserInput;
import com.tinqin.library.book.api.operations.update.unblockuser.UnBlockUserOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController extends BaseController {

    private final CreateUser createUser;
    private final BlockUser blockUser;
    private final UnBlockUser unblockUser;

    @PostMapping(APIRotes.API_USER)
    public ResponseEntity<?> createUser(@RequestBody CreateUserInput input) {

        Either<OperationError, CreateUserOutput> result = createUser.process(input);

        return mapToResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(APIRotes.BLOCK_USER)
    public ResponseEntity<?> blockUser(@PathVariable("userId") String userId) {

        BlockUserInput input = BlockUserInput
                .builder()
                .userId(userId)
                .build();

        Either<OperationError, BlockUserOutput> result = blockUser.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping(APIRotes.UNBLOCK_USER)
    public ResponseEntity<?> unBlockUser(@PathVariable("userId") String userId) {

        UnBlockUserInput input = UnBlockUserInput
                .builder()
                .userId(userId)
                .build();

        Either<OperationError, UnBlockUserOutput> result = unblockUser.process(input);

        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
