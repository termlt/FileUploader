package com.example.fileuploader.controller;

import com.example.fileuploader.model.Author;
import com.example.fileuploader.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
        try {
            Author savedAuthor = authorService.createAuthor(author.getName(), author.getSurname());
            return new ResponseEntity<>("Author added with ID: " + savedAuthor.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while adding author.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
