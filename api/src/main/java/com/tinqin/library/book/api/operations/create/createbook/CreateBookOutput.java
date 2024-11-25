package com.tinqin.library.book.api.operations.create.createbook;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class CreateBookOutput implements ProcessorResult {

    private UUID bookId;

}
