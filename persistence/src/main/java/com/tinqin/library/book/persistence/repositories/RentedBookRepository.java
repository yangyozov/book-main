package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.RentedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentedBookRepository extends JpaRepository<RentedBook, UUID> {


}
