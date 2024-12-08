package com.tinqin.library.book.core.processors.update;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscription;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscriptionInput;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscriptionOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tinqin.library.book.api.operations.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class StopSubscriptionProcessor implements StopSubscription {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, StopSubscriptionOutput> process(StopSubscriptionInput input) {

        return stopSubscription(input)
                .map(s -> StopSubscriptionOutput.builder().build())
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Subscription> stopSubscription(StopSubscriptionInput input) {

        return Try.of(() -> {
                    User user = userRepository.findById(input.getUserId())
                            .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

                    return checkAndUpdateSubscription(user);
                }
        );
    }

    private Subscription checkAndUpdateSubscription(User user) {

        Subscription userSubscription = user.getSubscription();

        if (userSubscription == null) {

            throw new BusinessException(NO_SUBSCRIPTION);
        }

        if (!userSubscription.getCanRent()) {

            throw new BusinessException(SUBSCRIPTION_STOPPED);
        }

        userSubscription.setCanRent(false);

        return subscriptionRepository.save(userSubscription);
    }
}
