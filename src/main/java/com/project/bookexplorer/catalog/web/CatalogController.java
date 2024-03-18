package com.project.bookexplorer.catalog.web;

import com.project.bookexplorer.catalog.application.port.CatalogUseCase;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.UpdateBookCoverCommand;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import com.project.bookexplorer.catalog.domain.Book;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/catalog")
@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author) {
        if (title.isPresent() && author.isPresent()) {
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return catalog.findByTitle(title.get());
        } else if (author.isPresent()) {
            return catalog.findByAuthor(author.get());
        } else {
            return catalog.findAll();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return catalog
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@PathVariable Long id, @Validated(UpdateValidation.class) @RequestBody RestBookCommand command) {
        UpdateBookResponse response = catalog.updateBook(command.toUpdateCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PatchMapping(value = "/{id}/cover", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addBookCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Got file: " + file.getOriginalFilename());
        catalog.updateBookCover(new UpdateBookCoverCommand(
                id,
                file.getBytes(),
                file.getContentType(),
                file.getOriginalFilename()
        ));
    }

    @DeleteMapping("/{id}/cover")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookCover(@PathVariable Long id) {
        catalog.removeBookCover(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Validated(CreateValidation.class) @RequestBody RestBookCommand command) {
        Book book = catalog.addBook(command.toCreateCommand());
        return book;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        catalog.removeById(id);
    }

    interface UpdateValidation{
    }

    interface CreateValidation{
    }

    @Data
    private static class RestBookCommand {

        @NotBlank(message = "Please provide a title", groups = {CreateValidation.class})
        private String title;

        @NotBlank(message = "Please provide an author", groups = {CreateValidation.class})
        private String author;

        @NotNull(groups = {CreateValidation.class})
        private Integer year;

        @NotNull(groups = {CreateValidation.class})
        @DecimalMin(value = "0.00", groups = {CreateValidation.class, UpdateValidation.class})
        private BigDecimal price;

        CreateBookCommand toCreateCommand() {
            return new CreateBookCommand(title, author, year, price);
        }

        UpdateBookCommand toUpdateCommand(Long id) {
            return new UpdateBookCommand(id, title, author, year, price);
        }
    }
}
