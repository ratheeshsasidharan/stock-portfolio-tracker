package com.rs.stockportfoliotracker.service;

import com.rs.stockportfoliotracker.model.Order;
import com.rs.stockportfoliotracker.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        Order orderWithDate = new Order(
                null,
                order.userId(),
                order.stockSymbol(),
                order.quantity(),
                order.price(),
                order.orderType(),
                java.time.LocalDateTime.now()
        );
        return orderRepository.save(orderWithDate);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
