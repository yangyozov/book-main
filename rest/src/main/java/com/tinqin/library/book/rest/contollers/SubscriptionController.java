package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.APIRotes;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscription;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscriptionInput;
import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscriptionOutput;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscription;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscriptionInput;
import com.tinqin.library.book.api.operations.update.stopsubscription.StopSubscriptionOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController extends BaseController {

    private final CreateSubscription createSubscription;
    private final StopSubscription stopSubscription;

    @PostMapping(APIRotes.API_SUBSCR)
    public ResponseEntity<?> createSubscription(@RequestBody CreateSubscriptionInput input) {

        Either<OperationError, CreateSubscriptionOutput> result = createSubscription.process(input);

        return mapToResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(APIRotes.API_SUBSCR)
    public ResponseEntity<?> stopSubscription(@RequestBody StopSubscriptionInput input) {

        Either<OperationError, StopSubscriptionOutput> result = stopSubscription.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }
}
