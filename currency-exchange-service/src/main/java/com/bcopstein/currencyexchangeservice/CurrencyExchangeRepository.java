package com.bcopstein.currencyexchangeservice;

import org.springframework.data.repository.CrudRepository;

public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange,Long> {
    CurrencyExchange findByFromAndTo(String from, String to);
}
