package com.tinqin.library.book.persistence.models;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "book_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private String bookStatus;
}
