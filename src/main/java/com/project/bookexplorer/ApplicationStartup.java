package com.project.bookexplorer;

import com.project.bookexplorer.catalog.application.CatalogController;
import com.project.bookexplorer.catalog.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = catalogController.findByTitle("The");
        books.forEach(System.out::println);
    }
}
