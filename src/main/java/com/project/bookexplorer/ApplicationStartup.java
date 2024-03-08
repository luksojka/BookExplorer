package com.project.bookexplorer;

import com.project.bookexplorer.catalog.application.CatalogController;
import com.project.bookexplorer.catalog.domain.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

    public ApplicationStartup(CatalogController catalogController) {
        this.catalogController = catalogController;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = catalogController.findByTitle("The");
        books.forEach(System.out::println);
    }
}
