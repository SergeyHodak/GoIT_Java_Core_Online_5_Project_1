package body.bank;


import java.io.IOException;

public interface CurrencyService {
    double getRate(Currency currency) throws IOException, InterruptedException;
}
