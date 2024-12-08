package com.tinqin.library.book.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqin.library.book.persistence.models.RentedBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class SubscriptionModel {

    private UUID identifyId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Boolean canRent;
    private List<RentedBook> rentedBookList;
}
