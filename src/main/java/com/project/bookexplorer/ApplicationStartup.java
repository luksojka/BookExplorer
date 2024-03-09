package com.project.bookexplorer;

import com.project.bookexplorer.catalog.application.CatalogController;
import com.project.bookexplorer.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;

    public ApplicationStartup(
            CatalogController catalogController,
            @Value("${bookexplorer.catalog.query}") String title
    ){
    this.catalogController = catalogController;
    this.title = title;
    }

    @Override
    public void run(String... args){
        List<Book> books = catalogController.findByTitle(title);
        books.forEach(System.out::println);
    }
}
