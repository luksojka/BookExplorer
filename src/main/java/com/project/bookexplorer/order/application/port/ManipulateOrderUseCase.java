package com.project.bookexplorer.order.application.port;

import com.project.bookexplorer.commons.Either;
import com.project.bookexplorer.order.domain.OrderItem;
import com.project.bookexplorer.order.domain.OrderStatus;
import com.project.bookexplorer.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public interface ManipulateOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, OrderStatus status);

    @Value
    @Builder
    @AllArgsConstructor
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    class PlaceOrderResponse extends Either<String, Long> {

        public PlaceOrderResponse(boolean success, String left, Long right){
            super(success, left, right);
        }

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, null, orderId);
        }

        public static PlaceOrderResponse failure(String error) {
            return new PlaceOrderResponse(false,error,null);
        }
    }
}
