package com.project.bookexplorer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService() {
        storage.put(1L, new Book(1L, "Diuna", "Frank Herbert", 1965));
        storage.put(2L, new Book(1L, "Okrakiem przez Atlantyk", "Andrzej Radomiński", 1982));
        storage.put(2L, new Book(1L, "The Hobbit", "J.R.R. Tolkien", 1960));
    }

    List<Book> findByTitle(String title) {
        return storage.values()
                      .stream()
                      .filter(book -> book.title.startsWith(title))
                      .collect(Collectors.toList());
    }
}
