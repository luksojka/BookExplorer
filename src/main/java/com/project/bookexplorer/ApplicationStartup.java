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
    private final String author;

    public ApplicationStartup(
            CatalogController catalogController,
            @Value("${bookexplorer.catalog.title}") String title,
            @Value("${bookexplorer.catalog.author}") String author
    ){
    this.catalogController = catalogController;
    this.title = title;
    this.author = author;
    }

    @Override
    public void run(String... args){
        List<Book> books = catalogController.findByTitle(title);
        List<Book> books2 = catalogController.findByAuthor(author);
        books.forEach(System.out::println);
        books2.forEach(System.out::println);
    }
}
