package com.ivanov.moexstockservice.dto;

import com.ivanov.moexstockservice.model.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> stocks;
}
