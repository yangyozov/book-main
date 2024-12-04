package com.tinqin.library.book.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private BigDecimal priceRental;
    private Integer stock;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd:mm:ss")
    private LocalDateTime updatedOn;
    private Boolean isDeleted;
}
