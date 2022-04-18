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
        bank = "NBU";
        quantityOfSignsAfterDot = 2;
        doNotify = false;
        notificationHour = 9;
    }
}