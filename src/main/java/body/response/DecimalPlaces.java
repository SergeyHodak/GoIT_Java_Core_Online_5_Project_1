package body.response;

import body.FSM.ChatSettings;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalPlaces {

    public static float rounding(float value){
        BigDecimal result = new BigDecimal(value);
        ChatSettings chatSettings = new ChatSettings();
        int decimalPlaces = chatSettings.getQuantityOfSignsAfterDot();
        result = result.setScale(decimalPlaces, RoundingMode.UP);
        return result.floatValue();
    }
}