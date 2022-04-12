import body.bank.CurrencyService;
import body.bank.PrivatBank.PrivatBank;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        CurrencyService currencyServicePB = new PrivatBank();
        HashMap<String, BigDecimal> rateUSD = currencyServicePB.getRate();

        System.out.println("PrivatBank: \n" + rateUSD);
    }
}
