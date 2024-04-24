package com.rs.stockportfoliotracker.model;

public record StockHoldingDetails(
        String stockSymbol,
        double quantity,
        double currentMarketValue,
        double currentMarketPrice) {
}
