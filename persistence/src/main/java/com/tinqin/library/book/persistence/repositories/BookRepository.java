package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {


}
