package com.tinqin.library.book.api.operations.create.createsubscription;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class CreateSubscriptionInput implements ProcessorInput {

    @NotBlank
    @Length(max = 100)
    private UUID userId;

    @NotBlank
    private LocalDate startDate;
}
