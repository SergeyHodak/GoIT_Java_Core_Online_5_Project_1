import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ParserMB {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("buy(Currency.USD) = " + buy(Currency.USD));
        System.out.println("buy(Currency.EUR) = " + buy(Currency.EUR));
        System.out.println("buy(Currency.USD) = " + buy(Currency.USD));
    }
    private static HttpResponse<String> sendRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String uri = "https://api.monobank.ua/bank/currency";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
    public static float buy (Currency currencyMB) throws IOException, InterruptedException, IllegalStateException {
        String stringOfCurrencies="";
        HttpResponse<String> response = sendRequest();
        if(response.statusCode()!=200){
            while (response.statusCode()!=200){
                Thread.sleep(2000);
                response = sendRequest();
                if(response.statusCode()==200){
                    stringOfCurrencies = String.valueOf(response.body());
                }
            }
        }
        else {
            stringOfCurrencies = String.valueOf(response.body());
        }

        Gson gson = new Gson();
        JsonMB[] currencies = gson.fromJson(stringOfCurrencies, JsonMB[].class);
        switch (currencyMB){
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
@Data
class JsonMB {
    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private float rateSell;
    private float rateBuy;
    private float rateCross;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
enum Currency {
    USD,
    EUR
}



