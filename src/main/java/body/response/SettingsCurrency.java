package body.response;

import java.math.BigDecimal;

public class SettingsCurrency {
    BigDecimal rateBuy = BigDecimal.valueOf(0.0);
    BigDecimal rateSell = BigDecimal.valueOf(0.0);
    String name; // EUR, USD
    String bankName; // NBU, PB, Mono

    public SettingsCurrency() {
    }

    public SettingsCurrency(String bankName, String currName) {
        this.name = currName;
        this.bankName = bankName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRateBuy(BigDecimal rateBuy) {
        this.rateBuy = rateBuy;
    }

    public void setRateSell(BigDecimal rateSell) {
        this.rateSell = rateSell;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRateBuy() {
        return rateBuy;
    }

    public BigDecimal getRateSell() {
        return rateSell;
    }

    public String getBankName() {
        return bankName;
    }

}