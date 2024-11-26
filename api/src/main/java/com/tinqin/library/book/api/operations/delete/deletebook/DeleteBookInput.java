package com.tinqin.library.book.api.operations.delete.deletebook;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

import static com.tinqin.library.book.api.operations.ValidationMessages.CLIENT_ID_CAN_NOT_BE_NULL;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class DeleteBookInput implements ProcessorInput {

    @UUID
    @NotBlank(message = CLIENT_ID_CAN_NOT_BE_NULL)
    private String bookId;

}
