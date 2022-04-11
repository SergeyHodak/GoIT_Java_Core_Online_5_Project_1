package body.bank.monobank;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Monobank  implements CurrencyService {
    private static final String url = "https://api.monobank.ua/bank/currency";
    private static JsonMB[] currencies;

    public Monobank() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        currencies = gson.fromJson(response.body(), JsonMB[].class);
    }

    @Override
    public float[] getBuy(Currency[] currency) {
        float[] result = new float[currency.length];
        for (int i = 0; i < currency.length; i++) {
            for (JsonMB mb : currencies) {
                if (mb.getCurrencyCodeA() == new ISO4217().get(currency[i])) {
                    result[i] = mb.getRateBuy();
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public float[] getSale(Currency[] currency) {
        float[] result = new float[currency.length];
        for (int i = 0; i < currency.length; i++) {
            for (JsonMB mb : currencies) {
                if (mb.getCurrencyCodeA() == new ISO4217().get(currency[i])) {
                    result[i] = mb.getRateSell();
                    break;
                }
            }
        }
        return result;
    }
}