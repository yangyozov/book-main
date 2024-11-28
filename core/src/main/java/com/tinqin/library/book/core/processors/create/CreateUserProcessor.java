package com.tinqin.library.book.core.processors.create;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.create.createusers.CreateUser;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserInput;
import com.tinqin.library.book.api.operations.create.createusers.CreateUserOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProcessor implements CreateUser {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateUserOutput> process(CreateUserInput input) {

        return createUser(input)
                .map(savedUser -> CreateUserOutput
                        .builder()
                        .userId(savedUser.getId())
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> createUser(CreateUserInput input) {

        return Try.of(() -> checkInputAndCreateUser(input));
    }

    private User checkInputAndCreateUser(CreateUserInput input) {

        User user = conversionService.convert(input, User.class);

        return userRepository.save(user);
    }
}
