package com.tinqin.library.book.core.processors.update;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUser;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUserInput;
import com.tinqin.library.book.api.operations.update.blockuser.BlockUserOutput;
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
public class BlockUserProcessor implements BlockUser {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, BlockUserOutput> process(BlockUserInput input) {

        UUID userId = UUID.fromString(input.getUserId());

        return findAndCheckUser(userId)
                .map(this::blockUser)
                .map(user -> BlockUserOutput.builder().build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<User> findAndCheckUser(UUID userId) {

        return Try.of(() -> userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(USER_NOT_FOUND)))
                .map(this::checkIsAdminAndBlocked);
    }

    private User checkIsAdminAndBlocked(User user) {

        if (user.getIsAdmin()) {

            throw new BusinessException(USER_IS_ADMIN);
        }

        if (user.getIsBlocked()) {

            throw new BusinessException(USER_IS_BLOCKED);
        }

        return user;
    }

    private User blockUser(User user) {

        user.setIsBlocked(true);

        return userRepository.save(user);
    }
}
