package com.project.bookexplorer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookExplorerOnlineStoreApplication implements CommandLineRunner {

    public BookExplorerOnlineStoreApplication(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public static void main(String[] args) {
		SpringApplication.run(BookExplorerOnlineStoreApplication.class, args);
	}

	private final CatalogService catalogService;

	@Override
	public void run(String... args) throws Exception {
		List<Book> books = catalogService.findByTitle("Diuna");
		books.forEach(System.out::println);
	}
}
