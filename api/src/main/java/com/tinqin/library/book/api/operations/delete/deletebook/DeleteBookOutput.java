package com.tinqin.library.book.api.operations.delete.deletebook;

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
public class DeleteBookOutput implements ProcessorResult {

    private UUID bookId;

}
