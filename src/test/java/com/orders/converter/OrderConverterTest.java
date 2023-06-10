package com.orders.converter;

import com.orders.converters.OrderConverter;
import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderConverterTest {

    @Test
    void testOrderDtoToOrder_ConvertsSuccessfully() {
        OrderDto orderDto = new OrderDto();
        orderDto.setName("Test Order");
        orderDto.setTotalPrice(BigDecimal.valueOf(100.00));
        orderDto.setTotalQuantity(5);

        OrderConverter orderConverter = new OrderConverter();
        Order result = orderConverter.orderDtoToOrder(orderDto);

        assertEquals(orderDto.getName(), result.getName());
        assertEquals(OrderStateEnum.CREATED, result.getState());
        assertEquals(LocalDate.now(), result.getOrderDate());
        assertEquals(orderDto.getTotalPrice(), result.getTotalPrice());
        assertEquals(orderDto.getTotalQuantity(), result.getTotalQuantity());
    }
}