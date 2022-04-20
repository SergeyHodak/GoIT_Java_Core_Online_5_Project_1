package body.response;

import body.bank.monobank.Monobank;
import body.bank.nbu.NBU;
import body.bank.pb.PrivatBank;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class BankResponse {

    public HashMap<String, BigDecimal> getCurrency(ChoiceBank choiceBank) throws IOException, InterruptedException {
        HashMap<String, BigDecimal> currency;
        switch (choiceBank) {
            case NBU:
                currency = new NBU().getRate();
                break;
            case PB:
                currency = new PrivatBank().getRate();
                break;
            case Monobank:
                currency = new Monobank().getRate();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choiceBank);
        }
        return currency;
    }
}