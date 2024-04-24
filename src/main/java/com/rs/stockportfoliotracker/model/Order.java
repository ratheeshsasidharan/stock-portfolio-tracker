package com.rs.stockportfoliotracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("orders")
public record Order(
    @Id
    Long id,
    Long userId,
    String stockSymbol,
    double quantity,
    double price,
    OrderType orderType,
    LocalDateTime orderDate
) {
}
