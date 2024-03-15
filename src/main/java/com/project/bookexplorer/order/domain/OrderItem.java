package com.project.bookexplorer.order.domain;

import com.project.bookexplorer.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
