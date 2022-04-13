import body.bank.CurrencyService;
import body.bank.monobank.Monobank;
import body.bank.nbu.NBU;
import body.bank.pb.PrivatBank;

import java.math.BigDecimal;
import java.util.HashMap;

public class TestParsing {
    public static void main(String[] args) throws Exception {
        CurrencyService currencyServicePB = new PrivatBank();
        CurrencyService currencyServiceMB = new Monobank();
        CurrencyService currencyServiceNBU = new NBU();
        HashMap<String, BigDecimal> ratePB = currencyServicePB.getRate();
        HashMap<String, BigDecimal> rateMB = currencyServiceMB.getRate();
        HashMap<String, BigDecimal> rateNBU = currencyServiceNBU.getRate();
        System.out.println("PrivatBank: " + ratePB );
        System.out.println("MonoBank: " + rateMB );
        System.out.println("NBU: " + rateNBU);
    }
}