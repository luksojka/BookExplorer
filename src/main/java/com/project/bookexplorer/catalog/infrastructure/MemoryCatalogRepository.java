package com.project.bookexplorer.catalog.infrastructure;

import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.catalog.domain.CatalogRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public MemoryCatalogRepository() {
        storage.put(1L, new Book(1L, "Diune", "Frank Herbert", 1965));
        storage.put(2L, new Book(2L, "Okrakiem przez Atlantyk", "Andrzej Radomiński", 1982));
        storage.put(3L, new Book(3L, "The Hobbit", "J.R.R. Tolkien", 1960));
        storage.put(4L, new Book(4L, "Harry Potter and the Sorcerer's Stone", "J. K. Rowling", 1997));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

}
