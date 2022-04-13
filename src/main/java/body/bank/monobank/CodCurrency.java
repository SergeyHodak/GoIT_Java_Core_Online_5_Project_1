package body.bank.monobank;

import body.bank.Currency;

public class CodCurrency {
    public int get(Currency currency) {
        switch (currency) {
            case USD:
                return 840;
            case EUR:
                return 978;
            default:
                return 0;
        }
    }
}