package com.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderDto {
    String name;
    int totalQuantity;
    BigDecimal totalPrice;
}
