package body.bank.monobank;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Monobank implements CurrencyService {
    private static final String url = "https://api.monobank.ua/bank/currency";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> buy_sale = List.of(new String[]{"Buy", "Sale"});

    private static HttpResponse<String> sendRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    @Override
    public HashMap<String, BigDecimal> getRate() throws IOException, InterruptedException {
        String stringOfCurrencies = "";
        HttpResponse<String> response = sendRequest();
        stringOfCurrencies = String.valueOf(response.body());


        Type typeToken = TypeToken
                .getParameterized(List.class, JsonMB.class)
                .getType();
        List<JsonMB> currencyItems = GSON.fromJson(stringOfCurrencies, typeToken);

        HashMap<String, BigDecimal> currencyMB = getCurrenciesOfBankInHashMap(currencyItems);

        return currencyMB;
    }

    private static HashMap<String, BigDecimal> getCurrenciesOfBankInHashMap(List<JsonMB> currencies) {
        HashMap<String, BigDecimal> currencyMap = new HashMap<>();
        for (Currency currency : Currency.values()) {
            List<BigDecimal> temp1 = getCurrenciesOfBank(new CodCurrency().get(currency), currencies);
            for (int j = 0; j < buy_sale.size(); j++) {
                currencyMap.put(currency + "mono" + buy_sale.get(j),
                        temp1.get(j));
            }
        }

        return currencyMap;
    }

    private static List<BigDecimal> getCurrenciesOfBank(Integer currency, List<JsonMB> currencies) {
        List<BigDecimal> res = new ArrayList<>();
        res.add(BigDecimal.valueOf(currencies.stream()
                .filter(it -> it.getCurrencyCodeA() == currency)
                .map(JsonMB::getRateBuy)
                .collect(Collectors.toList()).get(0)));
        res.add(BigDecimal.valueOf(currencies.stream()
                .filter(it -> it.getCurrencyCodeA() == currency)
                .map(JsonMB::getRateSell)
                .collect(Collectors.toList()).get(0)));

        return res;

    }
}