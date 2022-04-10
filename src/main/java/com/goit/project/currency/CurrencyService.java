package com.goit.project.currency;


import com.goit.project.currency.dto.Currency;

public interface CurrencyService {
    double getRate(Currency currency);
}
