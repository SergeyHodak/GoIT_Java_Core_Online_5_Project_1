package body.telegram.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        String userName = user.getFirstName();
        messageBuilder.append(convert("Вітаю")).append(" \uD83D\uDC4B\uD83C\uDFFB ")
                .append(userName).append("\n")
                .append(convert("даний бот допоможе відслідковувати актуальні курси валют"));

        SendMessage answer = new SendMessage();

        answer.setText(messageBuilder.toString());
        answer.setChatId(chat.getId().toString());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton but1 = new InlineKeyboardButton(convert("Отримати інформацію"));
        but1.setCallbackData("getInfoAbCourse");

        InlineKeyboardButton but2 = new InlineKeyboardButton(convert("Налаштування"));
        but2.setCallbackData("settings");
        rowInline.add(but1);
        rowInline.add(but2);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String convert(String text) {
        return new String(text.getBytes(), StandardCharsets.UTF_8);
    }
}