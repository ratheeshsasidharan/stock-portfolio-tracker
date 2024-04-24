package com.rs.stockportfoliotracker.service;

import com.rs.stockportfoliotracker.config.StockPrice;
import com.rs.stockportfoliotracker.model.Order;
import com.rs.stockportfoliotracker.model.OrderType;
import com.rs.stockportfoliotracker.model.StockHoldingDetails;
import com.rs.stockportfoliotracker.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StockHoldingService {

    private final OrderRepository orderRepository;
    private final RestClient restClient;

    public List<StockHoldingDetails> getStockHoldings() {
        log.info(Thread.currentThread() + " - Inside getStockHoldings");
        List<Order> orders = orderRepository.findAll();
        String uniqueStockSymbols = orders.stream().map(Order::stockSymbol).distinct().collect(Collectors.joining(","));
        Map<String, Double> stockPrices = getStockPrices(uniqueStockSymbols);
        return getStockHoldingDetails(orders, stockPrices);
    }

    private List<StockHoldingDetails> getStockHoldingDetails(List<Order> orders, Map<String, Double> stockPrices) {
        return orders.stream().collect(Collectors.groupingBy(Order::stockSymbol,
                Collectors.collectingAndThen(
                        Collectors.reducing(
                                new StockHoldingDetails(null, 0.0, 0.0, 0.0),
                                (Order o) -> new StockHoldingDetails(o.stockSymbol(), orderTypeFactor(o.orderType()) * o.quantity(),
                                        0.0, 0.0),
                                this::combineStockHoldingDetails
                        ),
                        (StockHoldingDetails s) -> this.populateMarketPriceAndMarketValue(s, stockPrices)
                )
        )).values().stream().toList();
    }

    private StockHoldingDetails populateMarketPriceAndMarketValue(StockHoldingDetails s, Map<String, Double> stockPrices) {
        double price = stockPrices.get(s.stockSymbol());
        return new StockHoldingDetails(s.stockSymbol(), s.quantity(),Math.round(s.quantity()*price), price);
    }

    private StockHoldingDetails combineStockHoldingDetails(StockHoldingDetails s1, StockHoldingDetails s2) {
        return new StockHoldingDetails(s2.stockSymbol(),s1.quantity() + s2.quantity(), 0.0,0.0);
    }


    private int orderTypeFactor(OrderType orderType) {
        return orderType == OrderType.BUY ? 1 : -1;
    }

    private Map<String, Double> getStockPrices(String stockSymbols) {
        return restClient.get()
                .uri("/" + stockSymbols + "?apikey=oJGKyZ9AMdEo4jpgaHyiPWUKrgnxPAcV")
                .retrieve()
                .body(new ParameterizedTypeReference<List<StockPrice>>() {})
                .stream()
                .collect(Collectors.toMap(StockPrice::symbol, StockPrice::price));
    }


}
