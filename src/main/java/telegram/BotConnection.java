package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotConnection extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return ConstantData.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return ConstantData.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Тестовое сообщение, о том что был отправлен текст от пользователя нашему боту");
    }
}