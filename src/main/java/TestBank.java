import body.bank.CurrencyService;
import body.bank.PrivatBank.PrivatBank;
import body.bank.Currency;
import body.bank.monobank.ParserMB;

import java.io.IOException;

public class TestBank {
    public static void main(String[] args) throws IOException, InterruptedException {
        CurrencyService currencyServicePB = new PrivatBank();
        CurrencyService currencyServiceMB = new ParserMB();

        double ratePB = currencyServicePB.getRate(Currency.EUR);
        double rateMB = currencyServiceMB.getRate(Currency.EUR);
        System.out.println("Bank : \n" + ratePB + "\n" + rateMB);


    }
}
