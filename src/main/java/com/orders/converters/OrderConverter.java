package com.orders.converters;

import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderConverter {

    public Order orderDtoToOrder(OrderDto orderDto) {
        return Order.builder()
                .name(orderDto.getName())
                .state(OrderStateEnum.CREATED)
                .orderDate(LocalDate.now())
                .totalPrice(orderDto.getTotalPrice())
                .totalQuantity(orderDto.getTotalQuantity())
                .build();
    }
}
