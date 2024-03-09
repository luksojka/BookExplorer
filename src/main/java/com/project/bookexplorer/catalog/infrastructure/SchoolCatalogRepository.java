package com.project.bookexplorer.catalog.infrastructure;

import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.catalog.domain.CatalogRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class SchoolCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public SchoolCatalogRepository() {
        storage.put(1L, new Book(1L, "Folwark zwierzęcy", "George Orwell", 1945));
        storage.put(2L, new Book(2L, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(3L, new Book(3L, "Tango", "Sławomir Mrożek", 1964));
        storage.put(4L, new Book(4L, "Lalka", "Bolesław Prus", 1889));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

}
