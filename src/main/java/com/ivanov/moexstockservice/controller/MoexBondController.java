package com.ivanov.moexstockservice.controller;

import com.ivanov.moexstockservice.dto.FigiesDto;
import com.ivanov.moexstockservice.dto.StocksDto;
import com.ivanov.moexstockservice.dto.StocksPricesDto;
import com.ivanov.moexstockservice.dto.TickersDto;
import com.ivanov.moexstockservice.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {

    private final BondService bondService;

    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {
        return bondService.getBondsFromMoex(tickersDto);
    }

    @PostMapping("/prices")
    public StocksPricesDto getPricesByFigies(FigiesDto figiesDto) {
        return bondService.getPricesByFigies(figiesDto);
    }
}
