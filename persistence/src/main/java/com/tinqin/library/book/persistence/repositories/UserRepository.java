package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
