package body.telegram.keyboardAction;

import body.telegram.BotConnection;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class KeyboardActions extends BotConnection {
    private String chatId;

    public KeyboardActions(String chatId) {
        this.chatId = chatId;
    }

    public void sendStart() {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Вітаю \uD83D\uDC4B\uD83C\uDFFB ").append("\n");
        messageBuilder.append("даний бот допоможе відслідковувати актуальні курси валют");

        SendMessage answer = new SendMessage();

        answer.setText(messageBuilder.toString());
        answer.setChatId(chatId);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(createRowKeyboardWith1Button("Отримати інформацію","getInfoAbCourse"));
        rowsInline.add(createRowKeyboardWith1Button("Налаштування","settings"));

        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //BotLogger.error(LOGTAG, e);
        }
    }

    public void setCurrency(Update u, boolean checkUSD, boolean checkEUR) {
        CallbackQuery callbackquery = u.getCallbackQuery();

        EditMessageText editMarkup = new EditMessageText();

        editMarkup.setChatId(chatId);
        editMarkup.setInlineMessageId(callbackquery.getInlineMessageId());
        editMarkup.setText("Виберіть валюту в якій ви хочете отримувати курс");
        editMarkup.enableMarkdown(true);
        editMarkup.setMessageId(callbackquery.getMessage().getMessageId());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInlineFirst = new ArrayList<>();
        List<InlineKeyboardButton> rowInlineSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowInlineThree = new ArrayList<>();
        InlineKeyboardButton but1 = new InlineKeyboardButton();
        but1.setCallbackData("USD");
        if (checkUSD) {
            but1.setText("\u2705 USD");
        } else {
            but1.setText("USD");
        }


        InlineKeyboardButton but2 = new InlineKeyboardButton("EUR");
        but2.setCallbackData("EUR");

        if (checkEUR) {
            but2.setText("\u2705 EUR");
        } else {
            but2.setText("EUR");
        }
        InlineKeyboardButton but3 = new InlineKeyboardButton("Назад");
        but3.setCallbackData("back");
        rowInlineFirst.add(but1);
        rowInlineSecond.add(but2);
        rowInlineThree.add(but3);

        rowsInline.add(rowInlineFirst);
        rowsInline.add(rowInlineSecond);
        rowsInline.add(rowInlineThree);

        markupInline.setKeyboard(rowsInline);

        editMarkup.setReplyMarkup(markupInline);
        try {
            execute(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendGetCurrency() {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Вот тобі і курс!!!");

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("FUCK error" + e);
        }
    }

    public void sendCountSignMenu() {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Виберіть кількість знаків після коми");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(createRowKeyboardWith1Button("2","2"));
        rowsInline.add(createRowKeyboardWith1Button("3","3"));
        rowsInline.add(createRowKeyboardWith1Button("4","4"));

        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("FUCK error" + e);
        }
    }

    public void sendBankMenu() {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Виберіть банк з якого хочете отримувати данні");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        rowsInline.add(createRowKeyboardWith1Button("Приват","privat"));
        rowsInline.add(createRowKeyboardWith1Button("Моно","monobank"));
        rowsInline.add(createRowKeyboardWith1Button("НБУ","nbu"));

        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("FUCK error" + e);
        }
    }

    private List<InlineKeyboardButton> createRowKeyboardWith1Button(String text, String callbackData) {
        List<InlineKeyboardButton> rowInlineFirst = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(callbackData);
        rowInlineFirst.add(button);
        return rowInlineFirst;
    }
    private List<InlineKeyboardButton> createRowKeyboardWith2Button(String textB1, String callbackDataB1,String textB2, String callbackDataB2) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton(textB1);
        button1.setCallbackData(callbackDataB1);
        InlineKeyboardButton button2 = new InlineKeyboardButton(textB2);
        button2.setCallbackData(callbackDataB2);

        rowInline.add(button1);
        rowInline.add(button2);

        return rowInline;



    }
    private List<InlineKeyboardButton> createRowKeyboardWith3Button(String textB1, String callbackDataB1,String textB2, String callbackDataB2,String textB3, String callbackDataB3) {
        List<InlineKeyboardButton> rowInlineFirst = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton(textB1);
        button1.setCallbackData(callbackDataB1);
        InlineKeyboardButton button2 = new InlineKeyboardButton(textB2);
        button2.setCallbackData(callbackDataB2);
        InlineKeyboardButton button3 = new InlineKeyboardButton(textB3);
        button3.setCallbackData(callbackDataB3);
        rowInlineFirst.add(button1);
        rowInlineFirst.add(button2);
        rowInlineFirst.add(button3);
        return rowInlineFirst;
    }

    public void sendSeetingsMenu() {

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Налаштування");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(createRowKeyboardWith1Button("Кількість знаків після коми","countSign"));
        rowsInline.add(createRowKeyboardWith1Button("Банк","bank"));
        rowsInline.add(createRowKeyboardWith1Button("Валюти","currency"));
        rowsInline.add(createRowKeyboardWith1Button("Час оповіщення","timeNotific"));
        rowsInline.add(createRowKeyboardWith1Button("Головна","main"));

        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("FUCK error" + e);
        }
    }

    public void sendTimeMenu() {

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Виберіть час оповіщення");


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        rowsInline.add(createRowKeyboardWith3Button("9","9","10","10","11","11"));
        rowsInline.add(createRowKeyboardWith3Button("12","12","13","13","14","14"));
        rowsInline.add(createRowKeyboardWith3Button("15","15","16","16","17","17"));
        rowsInline.add(createRowKeyboardWith2Button("18","18","Виключити оповіщення","off notific"));


        markupInline.setKeyboard(rowsInline);

        answer.setReplyMarkup(markupInline);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("FUCK error" + e);
        }
    }

}
