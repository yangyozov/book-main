package com.tinqin.library.book.api.operations.create.createauthor;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateAuthorOutput implements ProcessorResult {

    private UUID authorId;

}
