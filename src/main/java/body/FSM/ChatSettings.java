package body.FSM;

import lombok.Data;

@Data
public class ChatSettings {
    private boolean isUsdNeed;
    private boolean isEurNeed;
    private String bank;
    private int quantityOfSignsAfterDot;
    private boolean doNotify;
    private int notificationHour;

    /**
     * Default constructor
     */
    public ChatSettings(){
        isUsdNeed = true;
        isEurNeed = true;
        bank = "Monobank";
        quantityOfSignsAfterDot = 2;
        doNotify = true;
        notificationHour = 9;
    }
//    @Override
//    public String toString(){
//        String result = "";
//        result += ("isUsdNeed = " + isUsdNeed);
//        result += ("\nisEurNeed = " + isEurNeed);
//        result += ("\nbank = " + bank);
//        result += ("\nquantityOfSignsAfterDot =  = " + quantityOfSignsAfterDot);
//        result += ("\ndoNotify = " + doNotify);
//        result += ("\nnotificationHour = " + notificationHour);
//        return result;
//    }
}
