package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT authors FROM Author authors WHERE authors.id in ?1")
    List<Author> findAuthorsById(List<UUID> idAuthorList);
}
