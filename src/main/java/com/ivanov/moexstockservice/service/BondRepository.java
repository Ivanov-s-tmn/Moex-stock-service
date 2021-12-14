package com.ivanov.moexstockservice.service;

import com.ivanov.moexstockservice.dto.BondDto;
import com.ivanov.moexstockservice.exception.LimitRequestException;
import com.ivanov.moexstockservice.moexclient.CorporateBondsClient;
import com.ivanov.moexstockservice.moexclient.GovernmentBondsClient;
import com.ivanov.moexstockservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BondRepository {

    private final CorporateBondsClient corporateBondsClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final Parser parser;

    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds() {
        log.info("getting corporate bonds from moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestException("Moex isn't answering for getting corporate bonds.");
        }
        return bondDtos;
    }

    @Cacheable(value = "govs")
    public List<BondDto> getGovernmentBonds() {
        log.info("getting government bonds from moex");
        String xmlFromMoex = governmentBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestException("Moex isn't answering for getting government bonds.");
        }
        return bondDtos;
    }
}
