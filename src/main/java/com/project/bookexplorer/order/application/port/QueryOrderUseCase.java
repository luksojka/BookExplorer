package com.project.bookexplorer.order.application.port;

import com.project.bookexplorer.catalog.domain.Book;
import com.project.bookexplorer.order.domain.Order;
import com.project.bookexplorer.order.domain.OrderStatus;
import com.project.bookexplorer.order.domain.Recipient;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<ExtendedOrder> findAll();

    Optional<ExtendedOrder> findById(Long id);

    @Value
    class ExtendedOrder {
        Long id;
        OrderStatus status;
        List<ExtendedOrderItem> items;
        Recipient recipient;
        LocalDateTime createdAt;

        public BigDecimal totalPrice() {
            return items.stream()
                    .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @Value
    class ExtendedOrderItem{
        Book book;
        int quantity;
    }
}
