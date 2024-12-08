package com.tinqin.library.book.persistence.filereaderfactory.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {

    private final String title;
    private final Integer pages;
    private final BigDecimal price;
    private final BigDecimal priceRental;
    private final Integer stock;
    private final List<String> authors;
}
