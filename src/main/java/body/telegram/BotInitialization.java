package body.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotInitialization {
    public static void initialization() {
        BotConnection botConnection = new BotConnection();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(botConnection);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}