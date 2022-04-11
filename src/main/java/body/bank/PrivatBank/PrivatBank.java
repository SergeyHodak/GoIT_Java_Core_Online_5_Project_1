package body.bank.PrivatBank;

import body.bank.Currency;
import body.bank.CurrencyService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivatBank implements CurrencyService {
    private static final String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private final List<CurrencyItem> currencyItems;

    public PrivatBank() {
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
        currencyItems = new Gson().fromJson(json, typeToken);
    }

    @Override
    public float[] getBuy(Currency[] currency) {
        float[] result = new float[currency.length];
        for (int i = 0; i < currency.length; i++) {
            for (CurrencyItem pb : currencyItems) {
                if (pb.getCcy() == currency[i] & pb.getBase_ccy() == Currency.UAH) {
                    result[i] = pb.getBuy();
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
            for (CurrencyItem pb : currencyItems) {
                if (pb.getCcy() == currency[i] & pb.getBase_ccy() == Currency.UAH) {
                    result[i] = pb.getSale();
                    break;
                }
            }
        }
        return result;
    }
}