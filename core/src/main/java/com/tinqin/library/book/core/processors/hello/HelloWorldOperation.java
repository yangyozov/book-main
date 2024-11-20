package com.tinqin.library.book.core.processors.hello;

import com.tinqin.library.book.api.hello.HelloWorld;
import com.tinqin.library.book.api.hello.HelloWorldInput;
import com.tinqin.library.book.api.hello.HelloWorldResult;
import com.tinqin.library.book.persistence.models.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldOperation implements HelloWorld {
    @Override
    public HelloWorldResult process(HelloWorldInput input) {

        Book sdfsd = Book.builder().author("sdfsd").build();
        return HelloWorldResult
                .builder()
                .message("Hello from processor")
                .build();
    }
}
