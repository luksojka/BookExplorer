package com.project.bookexplorer;

import com.project.bookexplorer.catalog.application.port.CatalogUseCase;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.project.bookexplorer.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final String author;

    public ApplicationStartup(
            CatalogUseCase catalog,
            @Value("${bookexplorer.catalog.title}") String title,
            @Value("${bookexplorer.catalog.author}") String author
    ){
    this.catalog = catalog;
    this.title = title;
    this.author = author;
    }

    @Override
    public void run(String... args){
        initData();
        findByTitle();
        findByAuthor();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand( "Diune", "Frank Herbert", 1965));
        catalog.addBook(new CreateBookCommand( "Okrakiem przez Atlantyk", "Andrzej Radomiński", 1982));
        catalog.addBook(new CreateBookCommand( "The Hobbit", "J.R.R. Tolkien", 1960));
        catalog.addBook(new CreateBookCommand( "Harry Potter and the Sorcerer's Stone", "J. K. Rowling", 1997));
        catalog.addBook(new CreateBookCommand( "Folwark zwierzęcy", "George Orwell", 1945));
        catalog.addBook(new CreateBookCommand( "Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalog.addBook(new CreateBookCommand( "Tango", "Sławomir Mrożek", 1964));
        catalog.addBook(new CreateBookCommand( "Lalka", "Bolesław Prus", 1889));
    }

    private void findByAuthor() {
        List<Book> books2 = catalog.findByAuthor(author);
        books2.forEach(System.out::println);
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

}
