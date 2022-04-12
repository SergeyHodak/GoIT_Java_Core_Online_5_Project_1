package body.bank;


import java.io.IOException;

public interface CurrencyService {
    HashMap<String, BigDecimal> getRate() throws IOException, InterruptedException;
}
