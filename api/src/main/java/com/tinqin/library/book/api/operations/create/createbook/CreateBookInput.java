package com.tinqin.library.book.api.operations.create.createbook;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class CreateBookInput implements ProcessorInput {

    @NotBlank
    @Length(min = 100)
    private String title;

    @NotBlank
    @Length(min = 100)
    private List<String> author;

    @NotBlank
    @Length(min = 5)
    private String pages;

    @NotBlank
    @Length(min = 5)
    private String price;

}
