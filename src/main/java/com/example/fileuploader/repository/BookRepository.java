package com.example.fileuploader.repository;

import com.example.fileuploader.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsBookByAuthorNameAndTitle(String authorName, String title);
}
