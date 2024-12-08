package com.tinqin.library.book.api.operations.update.hidebook;

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
public class HideBookOutput implements ProcessorResult {

    private UUID bookId;

}
