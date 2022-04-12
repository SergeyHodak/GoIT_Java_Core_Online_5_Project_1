package body.bank.nbu;

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

public class NBU implements CurrencyService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> buy_sale = List.of(new String[]{"Buy", "Sale"});

    private static HttpResponse<String> sendRequest() throws IOException, InterruptedException {
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //    @Override
//    public float[] getBuy(Currency[] currency) throws IllegalStateException {
//        float[] result = new float[currency.length];
//        for (int i = 0; i < currency.length; i++) {
//            for (JsonNBU nbu : currencies) {
//                if (Objects.equals(nbu.getCc(), currency[i].name())) {
//                    result[i] = nbu.getRate();
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public float[] getSale(Currency[] currency) {
//        return getBuy(currency);
//    }
    public HashMap<String, BigDecimal> getRate() throws IOException, InterruptedException {
        String stringOfCurrencies = "";
        HttpResponse<String> response = sendRequest();
        stringOfCurrencies = String.valueOf(response.body());


        Type typeToken = TypeToken
                .getParameterized(List.class, JsonNBU.class)
                .getType();
        List<JsonNBU> currencyItems = GSON.fromJson(stringOfCurrencies, typeToken);

        HashMap<String, BigDecimal> currencyPB = getCurrenciesOfBankInHashMap(currencyItems);

        return currencyPB;
    }

    private static HashMap<String, BigDecimal> getCurrenciesOfBankInHashMap(List<JsonNBU> currencies) {
        HashMap<String, BigDecimal> currencyMap = new HashMap<>();
        for (Currency currency : Currency.values()) {
            List<BigDecimal> temp1 = getCurrenciesOfBank(currency, currencies);
            for (int j = 0; j < buy_sale.size(); j++) {
                currencyMap.put(currency + "nbu" + buy_sale.get(j),
                        temp1.get(j));
            }
        }
        return currencyMap;
    }

    private static List<BigDecimal> getCurrenciesOfBank(Currency currency, List<JsonNBU> currencies) {
        List<BigDecimal> res = new ArrayList<>();
        res.add(BigDecimal.valueOf(currencies.stream()
                .filter(it -> it.getCc() == currency)
                .map(JsonNBU::getRate)
                .collect(Collectors.toList()).get(0)));
        res.add(BigDecimal.valueOf(currencies.stream()
                .filter(it -> it.getCc() == currency)
                .map(JsonNBU::getRate)
                .collect(Collectors.toList()).get(0)));
        return res;

    }
}