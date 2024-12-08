package com.tinqin.library.book.core.processors.get;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.SubscriptionModel;
import com.tinqin.library.book.api.operations.get.getsubscription.GetSubscription;
import com.tinqin.library.book.api.operations.get.getsubscription.GetSubscriptionInput;
import com.tinqin.library.book.api.operations.get.getsubscription.GetSubscriptionOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tinqin.library.book.api.operations.ValidationMessages.NO_SUBSCRIPTION;
import static com.tinqin.library.book.api.operations.ValidationMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetSubscriptionProcessor implements GetSubscription {

    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetSubscriptionOutput> process(GetSubscriptionInput input) {

        return getSubscription(input)
                .map(subscriptionModel -> GetSubscriptionOutput
                        .builder()
                        .subscription(subscriptionModel)
                        .build())
                .toEither()
                .mapLeft(errorHandler::handle);

    }

    private Try<SubscriptionModel> getSubscription(GetSubscriptionInput input) {

        return Try.of(() -> findAndConvertToModel(input));
    }

    private SubscriptionModel findAndConvertToModel(GetSubscriptionInput input) {

        User user = userRepository.findById(input.getUserId()).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        Subscription subscription = user.getSubscription();

        if (subscription == null) {

            throw new BusinessException(NO_SUBSCRIPTION);
        }

        return SubscriptionModel
                .builder()
                .identifyId(user.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .canRent(subscription.getCanRent())
                .rentedBookList(subscription.getRentedBooks())
                .build();
    }
}
