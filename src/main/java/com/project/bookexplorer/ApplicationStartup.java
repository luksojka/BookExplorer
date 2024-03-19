package com.project.bookexplorer;

import com.project.bookexplorer.catalog.application.port.CatalogUseCase;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.project.bookexplorer.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.order.application.port.ManipulateOrderUseCase;
import com.project.bookexplorer.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.project.bookexplorer.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;
import com.project.bookexplorer.order.application.port.QueryOrderUseCase;
import com.project.bookexplorer.order.domain.OrderItem;
import com.project.bookexplorer.order.domain.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase manipulateOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final String author;

    public ApplicationStartup(
            CatalogUseCase catalog,
            ManipulateOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookexplorer.catalog.title}") String title,
            @Value("${bookexplorer.catalog.author}") String author
    ) {
        this.catalog = catalog;
        this.manipulateOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book hobbit = catalog.findOneByTitle("The Hobbit").orElseThrow(() -> new IllegalStateException("Cannot fin a book"));
        Book diune = catalog.findOneByTitle("Diune").orElseThrow(() -> new IllegalStateException("Cannot fin a book"));

        Recipient recipient = Recipient
                .builder()
                .name("John Average")
                .phone("123-456")
                .street("Randomstreet 2/3")
                .city("Warsaw")
                .zipCode("12-345")
                .email("johnaverage@example.com")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(hobbit.getId(), 16))
                .item(new OrderItem(diune.getId(), 7))
                .build();

        PlaceOrderResponse response = manipulateOrder.placeOrder(command);
        String result = response.handle(
                orderId -> "Created ORDER wirh id: " + orderId,
                error -> "Failed to created order: " + error
        );
        System.out.println(result);

        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order + " DETAILS: " + order);
                });
    }

    private void searchCatalog() {
        findByTitle();
        findByAuthor();
        findAndUpdate();
        findByAuthor();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Diune", "Frank Herbert", 1965, new BigDecimal("100.00")));
        catalog.addBook(new CreateBookCommand("Okrakiem przez Atlantyk", "Andrzej Radomiński", 1982, new BigDecimal("50.00")));
        catalog.addBook(new CreateBookCommand("The Hobbit", "J.R.R. Tolkien", 1960, new BigDecimal("70.50")));
        catalog.addBook(new CreateBookCommand("Harry Potter and the Sorcerer's Stone", "J. K. Rowling", 1997, new BigDecimal("150.79")));
        catalog.addBook(new CreateBookCommand("Folwark zwierzęcy", "George Orwell", 1945, new BigDecimal("66.10")));
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal("200.00")));
        catalog.addBook(new CreateBookCommand("Tango", "Sławomir Mrożek", 1964, new BigDecimal("30.00")));
        catalog.addBook(new CreateBookCommand("Lalka", "Bolesław Prus", 1889, new BigDecimal("22.70")));
    }

    private void findByAuthor() {
        List<Book> books2 = catalog.findByAuthor(author);
        books2.forEach(System.out::println);
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book....");
        catalog.findOneByTitleAndAuthor("Diune", "Frank Herbert")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Diune part 2")
                            .build();
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

}
