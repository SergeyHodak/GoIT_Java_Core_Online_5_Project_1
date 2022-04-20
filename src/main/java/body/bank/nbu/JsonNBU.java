package body.bank.nbu;

import body.bank.Currency;
import lombok.Data;

@Data
public class JsonNBU {
    private int r030;
    private String txt;
    private float rate;
    private Currency cc;
    private String exchangeDate;
}