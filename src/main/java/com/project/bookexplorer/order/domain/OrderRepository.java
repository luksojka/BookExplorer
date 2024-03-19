package com.project.bookexplorer.order.domain;

import com.project.bookexplorer.order.application.port.QueryOrderUseCase;
import com.project.bookexplorer.order.application.port.QueryOrderUseCase.ExtendedOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    List<Order> findAll();

    Optional<Order> findById(Long id);

    void deleteById(Long id);

}
