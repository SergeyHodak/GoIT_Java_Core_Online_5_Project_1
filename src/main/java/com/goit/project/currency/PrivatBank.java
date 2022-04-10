package com.goit.project.currency;

import com.goit.project.currency.dto.Currency;
import com.goit.project.currency.dto.CurrencyItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivatBank implements CurrencyService {
    @Override
    public double getRate(Currency currency) {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        //Get JSON
        String json;
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to Privat API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItem.class)
                .getType();
        List<CurrencyItem> currencyItems = new Gson().fromJson(json, typeToken);

        return currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyItem::getBuy)
                .findFirst()
                .orElseThrow();
    }

}
