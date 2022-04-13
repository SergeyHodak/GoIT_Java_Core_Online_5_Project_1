import java.math.BigDecimal;
import java.math.RoundingMode;
public class ВecimalPlaces {
    public static float rounding(int decomalPlaces,float value){
        BigDecimal result = new BigDecimal(value);
        result = result.setScale(decomalPlaces, RoundingMode.UP);
        return result.floatValue();
    }
}

