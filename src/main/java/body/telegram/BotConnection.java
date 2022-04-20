package body.telegram;

import body.FSM.StateMachineListener;
import body.FSM.FSM;
import body.telegram.command.StartCommand;
import body.telegram.keyboardAction.KeyboardActions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
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

    @Override
    public void processNonCommandUpdate(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

        if (!stateMashines.containsKey(chatId)) {
            FSM fsm = new FSM();
            try {
                fsm.addListener(new MessageListener(chatId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            stateMashines.put(chatId, fsm);
        }
        try {
            stateMashines.get(chatId).handle(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return ConstantData.BOT_TOKEN;
    }

    private class MessageListener implements StateMachineListener {
        private final String chatId;
        private final KeyboardActions keyboard;

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
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    String message = null;
                    try {
                        message = stateMashines.get(chatId).getInfo();

                        try {
                            LocalDateTime localDateTime = LocalDateTime.now();
                            if(stateMashines.get(chatId).chatSettings.getNotificationHour()==(localDateTime.getHour())) {
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setText(message);
                                sendMessage.setChatId(chatId);
                                execute(sendMessage);
                            }
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, c.getTime(), 86400000); // it is 24h
        }
    }

    private String convert(String text) {
        return new String(text.getBytes(), StandardCharsets.UTF_8);
    }
}