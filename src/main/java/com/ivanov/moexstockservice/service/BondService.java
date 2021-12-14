package com.ivanov.moexstockservice.service;

import com.ivanov.moexstockservice.dto.*;
import com.ivanov.moexstockservice.exception.BondNotFoundException;
import com.ivanov.moexstockservice.exception.LimitRequestException;
import com.ivanov.moexstockservice.model.Currency;
import com.ivanov.moexstockservice.model.Stock;
import com.ivanov.moexstockservice.moexclient.CorporateBondsClient;
import com.ivanov.moexstockservice.moexclient.GovernmentBondsClient;
import com.ivanov.moexstockservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BondService {
    private final BondRepository bondRepository;
    private final CacheManager cacheManager;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovernmentBonds());
        List<BondDto> resultBonds = allBonds.stream()
                .filter(bond -> tickersDto.getTickers().contains(bond.getTicker()))
                .collect(Collectors.toList());
        List<Stock> stocks = resultBonds.stream()
                .map(bond ->
                        Stock.builder()
                        .ticker(bond.getTicker())
                        .name(bond.getName())
                        .figi(bond.getTicker())
                        .type("Bond")
                        .currency(Currency.RUB)
                        .source("MOEX")
                        .build())
                .collect(Collectors.toList());
        return new StocksDto(stocks);
    }

    public StocksPricesDto getPricesByFigies(FigiesDto figiesDto) {
        log.info("Request for figies {}", figiesDto.getFigies());
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovernmentBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());
        figies.removeAll(allBonds.stream().map(b -> b.getTicker()).collect(Collectors.toList()));
        if(!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }
        List<StockPrice> prices = allBonds.stream()
                .filter(b -> figiesDto.getFigies().contains(b.getTicker()))
                .map(b -> new StockPrice(b.getTicker(), b.getPrice() * 10))
                .collect(Collectors.toList());
        return new StocksPricesDto(prices);
    }
}
