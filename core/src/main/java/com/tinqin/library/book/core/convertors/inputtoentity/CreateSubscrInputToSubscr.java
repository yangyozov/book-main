package com.tinqin.library.book.core.convertors.inputtoentity;

import com.tinqin.library.book.api.operations.create.createsubscription.CreateSubscriptionInput;
import com.tinqin.library.book.persistence.models.Subscription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateSubscrInputToSubscr implements Converter<CreateSubscriptionInput, Subscription> {

    @Override
    public Subscription convert(CreateSubscriptionInput source) {
        return Subscription
                .builder()
                .startDate(source.getStartDate())
                .build();
    }
}
