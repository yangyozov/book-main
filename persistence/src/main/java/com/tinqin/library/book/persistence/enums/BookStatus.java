package com.tinqin.library.book.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {

    PUBLISHED("Published"),
    UNPUBLISHED("Unpublished");

    private final String code;

    public String toString() {
        return code;
    }
}