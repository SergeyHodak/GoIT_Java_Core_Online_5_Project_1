package body.bank.nbu;

import lombok.Data;

@Data
public class JsonNBU {
    private int r030;
    private String txt;
    private float rate;
    private String cc;
    private String exchangedate;
}