package com.project.bookexplorer.catalog.application;

import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.catalog.domain.CatalogService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CatalogController {

    private final CatalogService service;

    CatalogController(CatalogService service) {
        this.service = service;
    }

    public List<Book> findByTitle(String title) {
        return service.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return service.findByAuthor(author);
    }
}
