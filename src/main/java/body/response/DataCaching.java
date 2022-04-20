package body.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DataCaching implements Runnable {

    private static volatile DataCaching instance;
    private static final CurrencyStorage currencyStorage = new CurrencyStorage();

    public DataCaching() {
    }

    public static DataCaching getInstance() {
        synchronized (DataCaching.class) {
            if (instance == null) {
                instance = new DataCaching();
            }
        }
        return instance;
    }

    private static class CurrencyStorage {
        HashMap<String, SettingsCurrency> currenciesNBU = new HashMap<>();
        HashMap<String, SettingsCurrency> currenciesPB = new HashMap<>();
        HashMap<String, SettingsCurrency> currenciesMono = new HashMap<>();
    }

    private void parseCurrencyResponse(HashMap<String, BigDecimal> curMap) throws Exception {

        for (Map.Entry<String, BigDecimal> entry : curMap.entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();
            SettingsCurrency currency;
            String abbr = key.substring(0, 3);
            if (!(abbr.equals("EUR") || abbr.equals("USD"))) {
                throw new Exception("Invalid currency name in currency rate '" + key + "'");
            }
            String oper = key.substring(key.length() - 3);
            if (!(oper.equals("Buy") || (key).endsWith("Sale"))) {
                throw new Exception("Invalid operation name in currency rate '" + key + "'");
            }
            String bank = key.substring(3, 5);

            switch (bank) {
                case "nb":
                    if (currencyStorage.currenciesNBU.containsKey(abbr)) {
                        currency = currencyStorage.currenciesNBU.get(abbr);
                    } else
                        currency = new SettingsCurrency("NBU", abbr);
                    currencyStorage.currenciesNBU.put(abbr, currency);
                    break;
                case "mo":
                    if (currencyStorage.currenciesMono.containsKey(abbr)) {
                        currency = currencyStorage.currenciesMono.get(abbr);
                    } else
                        currency = new SettingsCurrency("Mono", abbr);
                    currencyStorage.currenciesMono.put(abbr, currency);
                    break;
                case "pb":
                    if (currencyStorage.currenciesPB.containsKey(abbr)) {
                        currency = currencyStorage.currenciesPB.get(abbr);
                    } else
                        currency = new SettingsCurrency("PB", abbr);
                    currencyStorage.currenciesPB.put(abbr, currency);
                    break;
                default:
                    throw new Exception("Invalid bank name in currency rate '" + key + "'");
            }

            if (oper.equals("Buy")) {
                currency.setRateBuy(value);
            } else {
                currency.setRateSell(value);
            }
        }
    }

    public HashMap<String, SettingsCurrency> getCurrenciesByBank(String bank) throws Exception {
        switch (bank) {
            case "NBU":
                return currencyStorage.currenciesNBU;
            case "Monobank":
                return currencyStorage.currenciesMono;
            case "PB":
                return currencyStorage.currenciesPB;
            default:
                throw new Exception("Invalid bank name '" + bank + "', must be one of NBU, PB, Mono.");
        }
    }

    public synchronized void run() {
        {
            System.out.println("DataCaching running");

            do {
                try {
                    parseCurrencyResponse((new BankResponse()).getCurrency(ChoiceBank.NBU));
                    parseCurrencyResponse((new BankResponse()).getCurrency(ChoiceBank.PB));
                    parseCurrencyResponse((new BankResponse()).getCurrency(ChoiceBank.Monobank));
                    Thread.sleep(5 * 60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}