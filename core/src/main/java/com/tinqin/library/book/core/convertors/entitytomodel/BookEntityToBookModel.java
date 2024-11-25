package com.tinqin.library.book.core.convertors.entitytomodel;

import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToBookModel implements Converter<Book, BookModel> {

    @Override
    public BookModel convert(Book source) {
        return BookModel
                .builder()
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .price(source.getPrice())
                .stock(source.getStock())
                .createdAd(source.getCreatedAd())
                .updatedOn(source.getUpdatedOn())
                .build();
    }
}
