package body.bank;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrencyService {
    HashMap<String, BigDecimal> getRate() throws IOException, InterruptedException;
}