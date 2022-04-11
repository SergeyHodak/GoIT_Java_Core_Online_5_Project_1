package body.bank.monobank;

import body.bank.Currency;

public class ISO4217 {
    public int get(Currency value) {
        switch (value) {
            case USD:
                return 840;
            case EUR:
                return 978;
            case UAH:
                return 980;
            default:
                return 0;
        }
    }
}