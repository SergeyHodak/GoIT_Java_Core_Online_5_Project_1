package body.chat_manager;

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
        bank = "monobank";
        quantityOfSignsAfterDot = 2;
        doNotify = true;
        notificationHour = 9;
    }
}
