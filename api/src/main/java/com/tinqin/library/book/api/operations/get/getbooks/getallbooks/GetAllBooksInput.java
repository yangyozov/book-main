package com.tinqin.library.book.api.operations.get.getbooks.getallbooks;

import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Builder
@Getter
public class GetAllBooksInput implements ProcessorInput {

    Pageable pageable;
}
