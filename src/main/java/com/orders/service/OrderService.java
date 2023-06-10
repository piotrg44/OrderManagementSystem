package com.orders.service;

import com.orders.converters.OrderConverter;
import com.orders.dto.OrderDto;
import com.orders.enums.OrderStateEnum;
import com.orders.exceptions.NotFoundException;
import com.orders.model.Order;
import com.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String ERROR_MESSAGE_WITH_PLACEHOLDER_FOR_ID = "Order with id %d is not found";

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public Order save(OrderDto orderDto) {
        return orderRepository.save(orderConverter.orderDtoToOrder(orderDto));
    }

    public void changeStatus(long id, OrderStateEnum state) {
        orderRepository.findById(id).ifPresentOrElse(
                orderWithNewStatus -> {
                    orderWithNewStatus.setState(state);
                    orderRepository.save(orderWithNewStatus);
                },
                () -> {
                    throw new NotFoundException(String.format(ERROR_MESSAGE_WITH_PLACEHOLDER_FOR_ID, id));
                }
        );
    }

    public Order findById(long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new NotFoundException(String.format(ERROR_MESSAGE_WITH_PLACEHOLDER_FOR_ID, id));
        }
    }
}
