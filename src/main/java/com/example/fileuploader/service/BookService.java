package com.example.fileuploader.service;

import com.example.fileuploader.model.Book;
import com.example.fileuploader.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(List<Book> books) {
        Set<Book> uniqueBooks = removeDuplicates(books);

        for (Book book : uniqueBooks) {
            if (!isDuplicateInDatabase(book)) {
                if (isValidBook(book)) {
                    bookRepository.save(book);
                    logger.info("Book saved: {}", book.getTitle());
                } else {
                    logger.warn("Invalid book data: {}", book.getTitle());
                }
            }
        }
    }

    private Set<Book> removeDuplicates(List<Book> books) {
        Set<Book> uniqueBooks = new HashSet<>();
        Set<String> uniqueTitles = new HashSet<>();

        for (Book book : books) {
            if (uniqueTitles.add(book.getTitle())) {
                uniqueBooks.add(book);
            } else {
                logger.warn("Duplicate book in the file: {}", book.getTitle());
            }
        }

        return uniqueBooks;
    }

    private boolean isValidBook(Book book) {
        boolean isValid =  book.getTitle() != null && !book.getTitle().isEmpty() &&
                book.getAuthorName() != null && !book.getAuthorName().isEmpty() &&
                book.getAuthorSurname() != null && !book.getAuthorSurname().isEmpty() &&
                book.getPrice() > 0;

        if (!isValid) {
            logger.warn("Invalid book data: {}", book.getTitle());
        }

        return isValid;
    }

    private boolean isDuplicateInDatabase(Book book) {
        boolean isDuplicate = bookRepository.existsBookByAuthorNameAndTitle(book.getAuthorName(), book.getTitle());

        if (isDuplicate) {
            logger.warn("Duplicate book in the database: {}", book.getTitle());
        }

        return isDuplicate;
    }
}
