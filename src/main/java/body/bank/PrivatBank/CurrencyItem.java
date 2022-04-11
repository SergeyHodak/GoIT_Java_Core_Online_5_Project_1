package body.bank.PrivatBank;

import body.bank.Currency;
import lombok.Data;

@Data
public class CurrencyItem {
    private Currency ccy;
    private Currency base_ccy;
    private float buy;
    private float sale;
}