package com.project.bookexplorer.order.application;

import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.catalog.domain.CatalogRepository;
import com.project.bookexplorer.order.application.port.QueryOrderUseCase;
import com.project.bookexplorer.order.domain.Order;
import com.project.bookexplorer.order.domain.OrderItem;
import com.project.bookexplorer.order.domain.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;
    private final CatalogRepository catalogRepository;

    public List<ExtendedOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toExtendedOrder)
                .collect(Collectors.toList());
    }

    public Optional<ExtendedOrder> findById(Long id) {
        return repository.findById(id).map(this::toExtendedOrder);
    }

    private ExtendedOrder toExtendedOrder(Order order) {
        List<ExtendedOrderItem> extendedItems = toExtendedItems(order.getItems());
        return new ExtendedOrder(
                order.getId(),
                order.getStatus(),
                extendedItems,
                order.getRecipient(),
                order.getCreatedAt()
        );
    }

    private List<ExtendedOrderItem> toExtendedItems(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Book book = catalogRepository
                            .findById(item.getBookId())
                            .orElseThrow(() -> new IllegalStateException("Unable to find book with ID: " + item.getBookId()));
                    return new ExtendedOrderItem(book, item.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
