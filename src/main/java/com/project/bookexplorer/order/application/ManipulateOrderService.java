package com.project.bookexplorer.order.application;

import com.project.bookexplorer.order.application.port.ManipulateOrderUseCase;
import com.project.bookexplorer.order.domain.Order;
import com.project.bookexplorer.order.domain.OrderRepository;
import com.project.bookexplorer.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManipulateOrderService implements ManipulateOrderUseCase {

    private final OrderRepository repository;

    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }

    public void deleteOrderById(Long id) {
        repository.deleteById(id);
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        repository.findById(id)
                .ifPresent(order -> {
                    order.setStatus(status);
                    repository.save(order);
                });
    }
}
