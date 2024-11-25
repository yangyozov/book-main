package com.tinqin.library.book.api.models;

import com.tinqin.library.book.persistence.models.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {

    private String title;
    private List<Author> author;
    private String pages;
    private BigDecimal price;
    private Integer stock;
    private LocalDateTime createdAd;
    private LocalDateTime updatedOn;
}
