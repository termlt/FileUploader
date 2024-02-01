package com.example.fileuploader.repository;

import com.example.fileuploader.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author findByNameAndSurname(String name, String surname);
}
