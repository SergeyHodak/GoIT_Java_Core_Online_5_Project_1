package body.telegram;

import body.FSM.StateMachineListener;
import body.FSM.FSM;
import body.telegram.command.StartCommand;
import body.telegram.keyboardAction.KeyboardActions;
import lombok.Data;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Data
public class BotConnection extends TelegramLongPollingCommandBot {
    private Map<String, FSM> stateMashines;


    public BotConnection() {
        register(new StartCommand());

        stateMashines = new ConcurrentHashMap<>();

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(String.valueOf(message.getChatId()));
            commandUnknownMessage.setText("Відповідна команда '" + message.getText() + "' поки що невідома нашому боту)");
            try {
                absSender.execute(commandUnknownMessage);
            } catch (TelegramApiException e) {
                System.out.println();
            }
        });
    }

    @Override
    public String getBotUsername() {
        return ConstantData.BOT_NAME;
    }


    @Override
    public void processNonCommandUpdate(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

        if (!stateMashines.containsKey(chatId)) {
            FSM fsm = new FSM();
            fsm.addListener(new MessageListener(chatId));
            stateMashines.put(chatId, fsm);
        }
        stateMashines.get(chatId).handle(update);

    }

    @Override
    public String getBotToken() {
        return ConstantData.BOT_TOKEN;
    }

    private class MessageListener implements StateMachineListener {
        private String chatId;
        private KeyboardActions keyboard;
        public MessageListener(String chatId) {
            this.chatId = chatId;
            keyboard = new KeyboardActions(chatId);
        }

        @Override
        public KeyboardActions getKeyBoard() {
            return keyboard;
        }

        @Override
        public void onMessageReceived(String message) {
            System.out.println("Mes:" + message);
        }
    }

}