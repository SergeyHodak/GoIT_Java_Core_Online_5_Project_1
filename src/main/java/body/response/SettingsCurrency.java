package body.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SettingsCurrency {
    BigDecimal rateBuy = BigDecimal.valueOf(0.0);
    BigDecimal rateSell = BigDecimal.valueOf(0.0);
    String name;
    String bankName;

    public SettingsCurrency(String bankName, String currName) {
        this.name = currName;
        this.bankName = bankName;
    }
}