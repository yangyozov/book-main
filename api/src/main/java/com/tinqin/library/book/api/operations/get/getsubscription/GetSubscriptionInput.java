package com.tinqin.library.book.api.operations.get.getsubscription;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.CLIENT_ID_CAN_NOT_BE_NULL;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class GetSubscriptionInput implements ProcessorInput {

    @NotBlank(message = CLIENT_ID_CAN_NOT_BE_NULL)
    private UUID userId;
}
