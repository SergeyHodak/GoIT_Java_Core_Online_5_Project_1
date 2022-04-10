import com.goit.project.currency.CurrencyService;
import com.goit.project.currency.PrivatBank;
import com.goit.project.currency.dto.Currency;

public class TestPB {
    public static void main(String[] args) {
        CurrencyService currencyService = new PrivatBank();

        double rate = currencyService.getRate(Currency.EUR);
        System.out.println(rate);
    }
}
