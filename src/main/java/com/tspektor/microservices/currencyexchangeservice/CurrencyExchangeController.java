package com.tspektor.microservices.currencyexchangeservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
        @PathVariable String from,
        @PathVariable String to) {

        log.info("retrieveExchangeValue called with {} to {}", from, to);

        CurrencyExchange currencyExchange
            = repository.findByFromAndTo(from, to);

        if(currencyExchange ==null) {
            throw new RuntimeException
                ("Unable to Find data for " + from + " to " + to);
        }

        String port = environment.getProperty("local.server.port");

        //CHANGE-KUBERNETES
        String host = environment.getProperty("HOSTNAME");
        String version = "v11";

        currencyExchange.setEnvironment(port + " " + version + " " + host);

        return currencyExchange;
    }
}
