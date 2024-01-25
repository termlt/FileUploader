package com.example.fileuploader.controller;

import com.example.fileuploader.model.Book;
import com.example.fileuploader.service.BookService;
import com.example.fileuploader.util.CsvParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Book> books = CsvParser.parseCsvToBooks(reader);
            bookService.addBook(books);
            return new ResponseEntity<>("File uploaded and data saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred during file upload.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
