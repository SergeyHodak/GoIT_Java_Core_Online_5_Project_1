package body.bank.nbu;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class NBU  implements CurrencyService {
    private static final String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static JsonNBU[] currencies;

    public NBU() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        currencies = gson.fromJson(response.body(), JsonNBU[].class);
    }

    @Override
    public float[] getBuy(Currency[] currency) throws IllegalStateException {
        float[] result = new float[currency.length];
        for (int i = 0; i < currency.length; i++) {
            for (JsonNBU nbu : currencies) {
                if (Objects.equals(nbu.getCc(), currency[i].name())) {
                    result[i] = nbu.getRate();
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public float[] getSale(Currency[] currency) {
        return getBuy(currency);
    }
}