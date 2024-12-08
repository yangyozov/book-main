package com.tinqin.library.book.api.operations.get.getallbooks;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class GetAllBooksOutput implements ProcessorResult {

    private List<BookModel> books;
}
