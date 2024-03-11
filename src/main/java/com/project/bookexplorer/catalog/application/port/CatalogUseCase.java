package com.project.bookexplorer.catalog.application.port;

import com.project.bookexplorer.catalog.domain.Book;
import lombok.Value;

import java.util.List;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    void addBook(CreateBookCommand command);

    @Value
    class CreateBookCommand{
        String title;
        String author;
        Integer year;
    }
}

