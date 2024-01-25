package com.example.fileuploader.util;

import com.example.fileuploader.model.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public static List<Book> parseCsvToBooks(Reader reader) throws IOException {
        List<Book> books = new ArrayList<>();
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withTrim());

        for (CSVRecord csvRecord : csvParser) {
            Book book = new Book();
            book.setTitle(csvRecord.get("title"));
            book.setAuthorName(csvRecord.get("author_name"));
            book.setAuthorSurname(csvRecord.get("author_surname"));
            book.setPrice(Integer.parseInt(csvRecord.get("price")));

            books.add(book);
        }

        return books;
    }
}
