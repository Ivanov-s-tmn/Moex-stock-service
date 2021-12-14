package com.ivanov.moexstockservice.moexclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "govbonds", url = "${moex.bonds.gov.url}", configuration = FeignConfig.class)
public interface GovernmentBondsClient {
    @GetMapping
    String getBondsFromMoex();
}
