package com.orders.controller;

import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.exceptions.NotFoundException;
import com.orders.model.Order;
import com.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderDto));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable long id) {
        try {
            orderService.changeStatus(id, OrderStateEnum.CONFIRMED);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable long id) {
        try {
            orderService.changeStatus(id, OrderStateEnum.CANCELLED);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeOrder(@PathVariable long id) {
        try {
            orderService.changeStatus(id, OrderStateEnum.COMPLETED);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> orderInfo(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(orderService.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
