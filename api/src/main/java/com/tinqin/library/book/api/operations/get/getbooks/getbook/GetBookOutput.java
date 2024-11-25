package com.tinqin.library.book.api.operations.get.getbooks.getbook;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class GetBookOutput implements ProcessorResult {

    private BookModel book;
}
