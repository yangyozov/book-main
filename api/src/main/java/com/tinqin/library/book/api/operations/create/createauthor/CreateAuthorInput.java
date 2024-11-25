package com.tinqin.library.book.api.operations.create.createauthor;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class CreateAuthorInput implements ProcessorInput {

    @NotBlank
    @Length(min = 100)
    private String firstName;

    @NotBlank
    @Length(min = 100)
    private String lastName;
}
