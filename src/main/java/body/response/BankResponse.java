package body.response;

import body.bank.CurrencyService;
import body.bank.monobank.Monobank;
import body.bank.nbu.NBU;
import body.bank.pb.PrivatBank;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class BankResponse {

    CurrencyService currencyServicePB = new PrivatBank();
    CurrencyService currencyServiceMB = new Monobank();
    CurrencyService currencyServiceNBU = new NBU();

    public HashMap<String, BigDecimal> getCurrency(ChoiceBank choiceBank) throws IOException, InterruptedException {
        HashMap<String, BigDecimal> currency;
        switch (choiceBank){
            case NBU :
                currency = currencyServiceNBU.getRate();;
                break;
            case PB:
                currency = currencyServicePB.getRate();
                break;
            case Monobank:
                currency = currencyServiceMB.getRate();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choiceBank);
        }
        return currency;
    }
}