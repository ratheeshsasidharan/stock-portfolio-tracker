package com.rs.stockportfoliotracker.repository;

import com.rs.stockportfoliotracker.model.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long>{
}
