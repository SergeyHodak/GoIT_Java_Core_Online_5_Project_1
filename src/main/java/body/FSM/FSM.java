package body.FSM;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;

@Data
public class FSM {
    private StateMachineListener listener;
    public final ChatSettings chatSettings;

    public void addListener(StateMachineListener listener) {
        this.listener = listener;
    }

    private interface StateHandler {
        void handler();
    }

    private HashMap<ChatPlace, StateHandler> fsmChatPlace = new HashMap<>();
    private ChatPlace chatPlace;

    public FSM() {
        this.chatSettings = new ChatSettings();
        chatPlace = ChatPlace.MAIN_MENU;
        fsmChatPlace.put(ChatPlace.MAIN_MENU, this::stateMainMenu);
        fsmChatPlace.put(ChatPlace.SETTINGS, this::stateSettings);
        fsmChatPlace.put(ChatPlace.BANKS, this::stateBanks);
        fsmChatPlace.put(ChatPlace.QUANTITY_OF_DIGITS_AFTER_DOT, this::stateCountSignAfterDot);
        fsmChatPlace.put(ChatPlace.TIME_OF_NOTIFICATION, this::stateTimeNotific);
        fsmChatPlace.put(ChatPlace.CURRENCIES, this::stateCurrencies);
    }

    private String message;
    private Update update;

    public void handle(Update update) {
        this.update = update;
        message = update.getCallbackQuery().getData();
        fsmChatPlace.get(chatPlace).handler();
    }

    void stateMainMenu() {
        switch (message) {
            case ("settings"):
                settings();
                break;
            case ("getInfoAbCourse"):
                getInfoAbCurrency();
                break;
        }
    }

    void stateSettings() {
        switch (message) {
            case ("countSign"):
                setCountSign();
                break;
            case ("bank"):
                setBank();
                break;
            case ("currency"):
                setCurrency();
                break;
            case ("timeNotific"):
                setTimeNotific();
                break;
            case ("main"):
                setMainMenu();
                break;
        }
    }

    void stateBanks() {
        chatSettings.setBank(message);
        settings();
    }

    void stateCountSignAfterDot() {
        chatSettings.setQuantityOfSignsAfterDot(Integer.parseInt(message));
        settings();
    }

    void stateTimeNotific() {
        chatSettings.setNotificationHour(Integer.parseInt(message));
        settings();
    }

    void stateCurrencies() {

        switch (message) {
            case ("USD"):
                chatSettings.setUsdNeed(!chatSettings.isUsdNeed());
                listener.getKeyBoard().setCurrency(update, chatSettings.isUsdNeed(), chatSettings.isEurNeed());
                break;
            case ("EUR"):
                chatSettings.setEurNeed(!chatSettings.isEurNeed());
                listener.getKeyBoard().setCurrency(update, chatSettings.isUsdNeed(), chatSettings.isEurNeed());
                break;
            case ("back"):
                settings();
                break;
        }
    }

    void settings() {
        listener.getKeyBoard().sendSeetingsMenu();
        try {
            chatPlace = chatPlace.goToSettings();

        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    void getInfoAbCurrency() {
        listener.getKeyBoard().sendGetCurrency();
        setMainMenu();
    }

    void setCountSign() {
        listener.getKeyBoard().sendCountSignMenu();
        try {
            chatPlace = chatPlace.goToQuantityOfDigitsAfterDot();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    void setBank() {
        listener.getKeyBoard().sendBankMenu();

        try {
            chatPlace = chatPlace.goToBanks();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    void setCurrency() {
        listener.getKeyBoard().setCurrency(update, chatSettings.isUsdNeed(), chatSettings.isEurNeed());

        try {
            chatPlace = chatPlace.goToCurrencies();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    void setTimeNotific() {
        listener.getKeyBoard().sendTimeMenu();

        try {
            chatPlace = chatPlace.goToTimeOfNotification();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    void setMainMenu() {
        listener.getKeyBoard().sendStart();

        try {
            chatPlace = chatPlace.goToMainMenu();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }
}