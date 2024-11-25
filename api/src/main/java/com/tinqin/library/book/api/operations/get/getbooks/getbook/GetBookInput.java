package com.tinqin.library.book.api.operations.get.getbooks.getbook;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.library.book.api.operations.ValidationMessages.CLIENT_ID_CAN_NOT_BE_NULL;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class GetBookInput implements ProcessorInput {

    @UUID
    @NotBlank(message = CLIENT_ID_CAN_NOT_BE_NULL)
    private String bookId;
}
