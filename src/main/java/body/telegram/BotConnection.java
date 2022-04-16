package body.telegram;

import body.FSM.StateMachineListener;
import body.FSM.FSM;
import body.telegram.command.StartCommand;
import body.telegram.keyboardAction.KeyboardActions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode(callSuper = true)
@Data
public class BotConnection extends TelegramLongPollingCommandBot {
    private Map<String, FSM> stateMashines;

    public BotConnection() {
        register(new StartCommand());

        stateMashines = new ConcurrentHashMap<>();
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(String.valueOf(message.getChatId()));
            commandUnknownMessage.setText(convert("Відповідна команда \"") + message.getText()
                    + convert("\" поки що невідома нашому боту)"));
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

    @SneakyThrows
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

        public MessageListener(String chatId) throws Exception {
            this.chatId = chatId;
            keyboard = new KeyboardActions(chatId);
        }

        @Override
        public KeyboardActions getKeyBoard() {
            return keyboard;
        }

        @Override
        public void onMessageReceived() {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, stateMashines.get(chatId).getChatSettings().getNotificationHour());
            c.set(Calendar.MINUTE, 13);
            c.set(Calendar.SECOND, 00);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    String message = "it is info send at exactly default time. if only was pressed one of button \"setting or get infp\"";
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(message);
                    sendMessage.setChatId(chatId);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }, c.getTime(), 86400000); // it is 24h
        }
    }

}