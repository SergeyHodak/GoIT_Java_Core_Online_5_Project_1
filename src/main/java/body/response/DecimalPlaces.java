package body.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalPlaces {
    public static float rounding(int decimalPlaces,float value){
        BigDecimal result = new BigDecimal(value);
        result = result.setScale(decimalPlaces, RoundingMode.UP);
        return result.floatValue();
    }
}
