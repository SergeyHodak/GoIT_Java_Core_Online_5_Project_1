package body.FSM;

import body.response.DataCaching;
import body.response.SettingsCurrency;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.naming.OperationNotSupportedException;
import java.math.RoundingMode;
import java.util.HashMap;

public class FSM {
    private StateMachineListener listener;
    public final ChatSettings chatSettings;

    public void addListener(StateMachineListener listener) {
        this.listener = listener;
    }

    private interface StateHandler {
        void handler() throws Exception;
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

    public void handle(Update update) throws Exception {
        this.update = update;
        message = update.getCallbackQuery().getData();
        fsmChatPlace.get(chatPlace).handler();
    }

    void stateMainMenu() throws Exception {
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

    void getInfoAbCurrency() throws Exception {
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

    public String getInfo() throws Exception {
        String bank = chatSettings.getBank();
        boolean usd = chatSettings.isUsdNeed();
        boolean eur = chatSettings.isEurNeed();
        int rounding = chatSettings.getQuantityOfSignsAfterDot();
        DataCaching data = DataCaching.getInstance();
        HashMap<String, SettingsCurrency> currenciesData = data.getCurrenciesByBank(bank);
        return resultStringForming(bank, usd, eur, rounding, currenciesData);
    }

    private String resultStringForming(String bank, boolean usd, boolean eur, int rounding,
                                       HashMap<String, SettingsCurrency> currenciesData) throws Exception {
        StringBuilder result = new StringBuilder();
        String bankString;
        switch (bank) {
            case "NBU":
                bankString = "НБУ";
                break;
            case "Monobank":
                bankString = "Монобанк";
                break;
            case "PB":
                bankString = "Приват банк";
                break;
            default:
                throw new Exception("Invalid bank name '" + bank + "', must be one of NBU, PB, Mono.");
        }
        if (!usd && !eur) {
            result.append("Не вибрана валюта сповіщення\n\n");
        } else {
            result.append("Курс валют в ")
                    .append(bankString)
                    .append(":\n\n");
        }
        if (usd) {
            result.append("USD/UAH\nКупівля: ");
            result.append(currenciesData.get("USD").getRateBuy().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\nПродаж: ");
            result.append(currenciesData.get("USD").getRateSell().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\n\n");
        }
        if (eur) {
            result.append("EUR/UAH\nКупівля: ");
            result.append(currenciesData.get("EUR").getRateBuy().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\nПродаж: ");
            result.append(currenciesData.get("EUR").getRateSell().setScale(rounding, RoundingMode.HALF_UP));
            result.append("\n\n");
        }

        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
