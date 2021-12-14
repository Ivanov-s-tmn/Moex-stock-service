package com.ivanov.moexstockservice.parser;

import com.ivanov.moexstockservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String ratesAsString);
}
