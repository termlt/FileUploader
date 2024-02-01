package com.example.fileuploader.service;

import com.example.fileuploader.model.Author;
import com.example.fileuploader.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String name, String surname) {
        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author updateAuthor(UUID id, String newName, String newSurname) throws Exception {
        if (id == null) {
            throw new Exception("Id can not be empty.");
        }

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(newName);
            author.setSurname(newSurname);
            return authorRepository.save(author);
        }

        return null;
    }

    public boolean deleteAuthor(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("Id can not be empty.");
        }

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            authorRepository.deleteById(id);
            return true;
        }

        return false;
    }
}