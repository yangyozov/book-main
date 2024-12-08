package com.tinqin.library.book.api.operations.update.stopsubscription;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class StopSubscriptionInput implements ProcessorInput {

    @NotBlank
    @Length(min = 100)
    private UUID userId;
}
