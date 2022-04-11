package body.bank.nbu;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class ParserNBU {
    public float buy(Currency currencyNBU) throws IOException, InterruptedException, IllegalStateException {
        String uri = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonNBU[] currencies = gson.fromJson(response.body(), JsonNBU[].class);
        System.out.println(Arrays.toString(currencies));

        switch (currencyNBU) {
            case EUR:
                for (JsonNBU currency : currencies) {
                    if (currency.getCc() == Currency.EUR) {
                        return currency.getRate();
                    }
                }

            case USD:
                for (JsonNBU currency : currencies) {
                    if (currency.getCc() == Currency.USD) {
                        return currency.getRate();
                    }
                }

        }
        return 0;
    }
}
