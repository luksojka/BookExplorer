package com.project.bookexplorer.catalog.application;

import com.project.bookexplorer.catalog.application.port.CatalogUseCase;
import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.catalog.domain.CatalogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;

    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().startsWith(author))
                .collect(Collectors.toList());
    }

    public void addBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        repository.save(book);
    }

}
