package com.project.bookexplorer.order.application;

import com.project.bookexplorer.order.application.port.QueryOrderUseCase;
import com.project.bookexplorer.order.domain.Order;
import com.project.bookexplorer.order.domain.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }
}
