package body.bank;

public interface CurrencyService {
    float[] getBuy(Currency[] currency);
    float[] getSale(Currency[] currency);
}