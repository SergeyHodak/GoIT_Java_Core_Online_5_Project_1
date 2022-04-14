package body.response;

import java.math.BigDecimal;
import lombok.Data;

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