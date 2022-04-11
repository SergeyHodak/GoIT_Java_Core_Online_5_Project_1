package body.bank.nbu;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NBU implements CurrencyService {
    public double getRate(Currency currencyNBU) throws IOException, InterruptedException, IllegalStateException {
        String uri = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonNBU[] currencies = gson.fromJson(response.body(), JsonNBU[].class);

        switch (currencyNBU) {
            case EUR:
                for (JsonNBU currency : currencies) {
                    if (currency.getCc().equals(Currency.EUR.name())) {
                        return currency.getRate();
                    }
                }

            case USD:
                for (JsonNBU currency : currencies) {
                    if (currency.getCc().equals(Currency.USD.name())) {
                        return currency.getRate();
                    }
                }

        }
        return 0;
    }
}
