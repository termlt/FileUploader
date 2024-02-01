package com.example.fileuploader.repository;

import com.example.fileuploader.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsBookByAuthorNameAndTitle(String authorName, String title);
}
