package com.tinqin.library.book.api.operations.update.purchasebook;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.CLIENT_ID_CAN_NOT_BE_NULL;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class PurchaseBookInput implements ProcessorInput {

    @NotBlank(message = CLIENT_ID_CAN_NOT_BE_NULL)
    private UUID bookId;

    @NotBlank(message = CLIENT_ID_CAN_NOT_BE_NULL)
    private UUID userId;
}
