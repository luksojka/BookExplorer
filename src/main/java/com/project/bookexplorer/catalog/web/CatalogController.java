package com.project.bookexplorer.catalog.web;

import com.project.bookexplorer.catalog.application.port.CatalogUseCase;
import com.project.bookexplorer.catalog.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/catalog")
@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    public List<Book> getAll() {
        return catalog.findAll();
    }
}
