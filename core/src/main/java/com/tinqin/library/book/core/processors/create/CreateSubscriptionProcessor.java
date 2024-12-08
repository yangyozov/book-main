package com.tinqin.library.book.core.processors.create;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscription;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscriptionInput;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscriptionOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static com.tinqin.library.book.api.operations.ValidationMessages.SUBSCR_NOT_CREATED;
import static com.tinqin.library.book.api.operations.ValidationMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CreateSubscriptionProcessor implements CreateSubscription {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateSubscriptionOutput> process(CreateSubscriptionInput input) {
        return createSubscription(input)
                .map(s -> CreateSubscriptionOutput
                        .builder()
                        .subscriptionId(s.getId())
                        .startDate(s.getStartDate())
                        .endDate(s.getEndDate())
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);

    }

    private Try<Subscription> createSubscription(CreateSubscriptionInput input) {

        return Try.of(() -> checkInputAndCreate(input));
    }

    private Subscription checkInputAndCreate(CreateSubscriptionInput input) {

        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        try {

            Subscription activeSubscription = user.getSubscription();

            if (activeSubscription != null) {

                activeSubscription.setEndDate(activeSubscription.getEndDate().plusDays(30));

                return subscriptionRepository.save(activeSubscription);
            } else {
                Subscription subscription = conversionService.convert(input, Subscription.class);

                Subscription savedSubscription = subscriptionRepository.save(subscription);

                user.setSubscription(savedSubscription);
                userRepository.save(user);

                return savedSubscription;
            }
        } catch (RuntimeException e) {

            throw new BusinessException(SUBSCR_NOT_CREATED);
        }
    }
}
