package com.orders.service;

import com.orders.converters.OrderConverter;
import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.exceptions.NotFoundException;
import com.orders.model.Order;
import com.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderConverter orderConverter;
    @InjectMocks
    private OrderService orderService;

    @Test
    void save_ValidOrderDto_OrderSaved() {
        // given
        OrderDto orderDto = new OrderDto();
        Order order = new Order();

        when(orderConverter.orderDtoToOrder(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        // when
        Order savedOrder = orderService.save(orderDto);

        // then
        verify(orderConverter).orderDtoToOrder(orderDto);
        verify(orderRepository).save(order);
        assertEquals(order, savedOrder);
    }

    @Test
    void changeStatus_OrderExists_StatusChanged() {
        // given
        long orderId = 1L;
        OrderStateEnum newState = OrderStateEnum.CONFIRMED;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setState(OrderStateEnum.CREATED);

        // when
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        orderService.changeStatus(orderId, newState);

        // then
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(existingOrder);
        assertEquals(newState, existingOrder.getState());
    }

    @Test
    void changeStatus_OrderNotFound_ThrowsNotFoundException() {
        // given
        long orderId = 1L;
        OrderStateEnum newState = OrderStateEnum.CONFIRMED;

        // when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () -> orderService.changeStatus(orderId, newState));
    }

    @Test
    void testFindById_ExistingOrder_ReturnsOrder() {
        long orderId = 1L;
        Order existingOrder = new Order(orderId, "Order 1", 10, BigDecimal.TEN, LocalDate.of(2023, 10, 20), OrderStateEnum.CREATED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        Order result = orderService.findById(orderId);

        assertEquals(existingOrder, result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFindById_NonExistingOrder_ThrowsNotFoundException() {
        long orderId = 2L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }
}
