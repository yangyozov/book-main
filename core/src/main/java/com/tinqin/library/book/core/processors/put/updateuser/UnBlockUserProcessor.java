package com.tinqin.library.book.core.processors.put.updateuser;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.put.updateuser.unblockuser.UnBlockUser;
import com.tinqin.library.book.api.operations.put.updateuser.unblockuser.UnBlockUserInput;
import com.tinqin.library.book.api.operations.put.updateuser.unblockuser.UnBlockUserOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class UnBlockUserProcessor implements UnBlockUser {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, UnBlockUserOutput> process(UnBlockUserInput input) {

        UUID userId = UUID.fromString(input.getUserId());

        return findAndCheckUser(userId)
                .map(this::unBlockUser)
                .map(user -> UnBlockUserOutput.builder().build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> findAndCheckUser(UUID userId) {

        return Try.of(() -> userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(USERS_NOT_FOUND)))
                .map(this::checkIsUnBlocked);
    }

    private User checkIsUnBlocked(User user) {

        if (!user.getIsBlocked()) {

            throw new BusinessException(USER_NOT_BLOCKED);
        }

        return user;
    }

    private User unBlockUser(User user) {

        user.setIsBlocked(false);

        return userRepository.save(user);
    }
}
