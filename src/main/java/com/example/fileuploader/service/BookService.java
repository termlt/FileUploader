package com.example.fileuploader.service;

import com.example.fileuploader.model.Author;
import com.example.fileuploader.model.Book;
import com.example.fileuploader.repository.AuthorRepository;
import com.example.fileuploader.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBooks(List<Book> books) throws Exception {
        Set<Book> uniqueBooks = removeDuplicates(books);

        for (Book book : uniqueBooks) {
            if (!isDuplicateInDatabase(book)) {
                if (isValidBook(book)) {
                    Author author = authorRepository.findByNameAndSurname(
                            book.getAuthor().getName(), book.getAuthor().getSurname());

                    if (author == null) {
                        throw new Exception("Author doesn't exist.");
                    }

                    book.setAuthor(author);

                    bookRepository.save(book);
                    logger.info("Book saved: {}", book.getTitle());
                } else {
                    logger.warn("Invalid book data: {}", book.getTitle());
                }
            }
        }
    }

    public boolean deleteBook(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("Id can not be empty.");
        }

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }

        return false;
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
                book.getAuthor().getName() != null && !book.getAuthor().getName().isEmpty() &&
                book.getAuthor().getSurname() != null && !book.getAuthor().getSurname().isEmpty() &&
                book.getPrice() > 0;

        if (!isValid) {
            logger.warn("Invalid book data: {}", book.getTitle());
        }

        return isValid;
    }

    private boolean isDuplicateInDatabase(Book book) {
        boolean isDuplicate = bookRepository.existsBookByAuthorNameAndTitle(book.getAuthor().getName(), book.getTitle());

        if (isDuplicate) {
            logger.warn("Duplicate book in the database: {}", book.getTitle());
        }

        return isDuplicate;
    }
}
