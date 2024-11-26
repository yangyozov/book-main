package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

}
