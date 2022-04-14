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
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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
        NotificationSender notificationSender = new NotificationSender(chatId); // it is added by Julia for notification

        if (!stateMashines.containsKey(chatId)) {
            FSM fsm = new FSM();
            fsm.addListener(new MessageListener(chatId));
            stateMashines.put(chatId, fsm);
        }
        notificationSender.sendNotification(chatId, stateMashines.get(chatId).getChatSettings().getNotificationHour());
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
    private String convert(String text) {
        return new String(text.getBytes(), StandardCharsets.UTF_8);
    }

    @Data
    public class NotificationSender {
        private String chatId;

        public NotificationSender(String chatId) {
            this.chatId = chatId;
        }
        // it is note for Julia M - will be deleted at the end
        //    Iterator<Map.Entry<String, Integer>> iterator = dataHolder.entrySet().iterator();
        //    while (dataHolder.hasNext()) {
        //        Map.Entry<String, Integer> entry = iterator.next();
        //        System.out.println(entry.getKey() + ":" + entry.getValue())
        //        sendNotification(entry.getKey(), ?entry.getValue().getChatSetting.getTimeNotification);
        //    }


        public void sendNotification(String chatId, int time) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, time);
            c.set(Calendar.MINUTE, 45);
            c.set(Calendar.SECOND, 45);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // there is needed to be called method getInfo which gives String text

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