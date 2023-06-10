package com.orders.controller;

import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.exceptions.NotFoundException;
import com.orders.model.Order;
import com.orders.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void createOrder_ReturnsCreatedStatus() {
        // given
        OrderDto orderDto = new OrderDto();
        when(orderService.save(any(OrderDto.class))).thenReturn(new Order());

        // when
        ResponseEntity<Order> response = orderController.createOrder(orderDto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(orderService, times(1)).save(any(OrderDto.class));
    }

    @Test
    void confirmOrder_ReturnsOkStatus() {
        // given
        long orderId = 1L;
        doNothing().when(orderService).changeStatus(orderId, OrderStateEnum.CONFIRMED);

        // when
        ResponseEntity<Void> response = orderController.confirmOrder(orderId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.CONFIRMED);
    }

    @Test
    void confirmOrder_ReturnsNoContentStatus() {
        // given
        long orderId = 1L;
        doThrow(NotFoundException.class).when(orderService).changeStatus(orderId, OrderStateEnum.CONFIRMED);

        // when
        ResponseEntity<Void> response = orderController.confirmOrder(orderId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.CONFIRMED);
    }

    @Test
    void cancelOrder_ReturnsOkStatus() {
        // given
        long orderId = 1L;
        doNothing().when(orderService).changeStatus(orderId, OrderStateEnum.CANCELLED);

        // when
        ResponseEntity<Void> response = orderController.cancelOrder(orderId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.CANCELLED);
    }

    @Test
    void cancelOrder_ReturnsNoContentStatus() {
        // given
        long orderId = 1L;
        doThrow(NotFoundException.class).when(orderService).changeStatus(orderId, OrderStateEnum.CANCELLED);

        // when
        ResponseEntity<Void> response = orderController.cancelOrder(orderId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.CANCELLED);
    }

    @Test
    void completeOrder_ReturnsOkStatus() {
        // given
        long orderId = 1L;
        doNothing().when(orderService).changeStatus(orderId, OrderStateEnum.COMPLETED);

        // when
        ResponseEntity<Void> response = orderController.completeOrder(orderId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.COMPLETED);
    }

    @Test
    void completeOrder_ReturnsNoContentStatus() {
        // given
        long orderId = 1L;
        doThrow(NotFoundException.class).when(orderService).changeStatus(orderId, OrderStateEnum.COMPLETED);

        // when
        ResponseEntity<Void> response = orderController.completeOrder(orderId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).changeStatus(orderId, OrderStateEnum.COMPLETED);
    }

    @Test
    void orderInfo_WithValidId_ReturnsOrder() {
        // given
        long orderId = 1L;
        Order order = new Order();
        when(orderService.findById(orderId)).thenReturn(order);

        // when
        ResponseEntity<Order> response = orderController.orderInfo(orderId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).findById(orderId);
    }

    @Test
    void orderInfo_WithInvalidId_ReturnsNoContentStatus() {
        // given
        long orderId = 1L;
        doThrow(NotFoundException.class).when(orderService).findById(orderId);

        // when
        ResponseEntity<Order> response = orderController.orderInfo(orderId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).findById(orderId);
    }
}