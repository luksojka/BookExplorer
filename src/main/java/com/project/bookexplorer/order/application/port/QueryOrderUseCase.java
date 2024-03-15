package com.project.bookexplorer.order.application.port;

import com.project.bookexplorer.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
