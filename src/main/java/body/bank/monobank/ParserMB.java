package body.bank.monobank;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ParserMB implements CurrencyService{
    @Override
    public double getRate(Currency currency) throws IllegalStateException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String uri = "https://api.monobank.ua/bank/currency";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonMB[] currencies = gson.fromJson(response.body(), JsonMB[].class);
        switch (currency){
            case EUR :
                for (JsonMB monoBankCurrency : currencies) {
                    if (monoBankCurrency.getCurrencyCodeA()== 978) {
                        return monoBankCurrency.getRateBuy();
                    }
                }


            case USD :
                for (JsonMB monoBankCurrency : currencies) {
                    if (monoBankCurrency.getCurrencyCodeA() == 840) {
                        return monoBankCurrency.getRateBuy();
                    }
                }

        }
        return Float.parseFloat(null);
    }
}

