package body.notificationSender;

import lombok.Data;

import java.util.*;

@Data
public class NotificationSender {
    // gitthis peace of code should be implemented in the right place.
    // probably in BotConnection class
    // from the Map with data dataHolder <chatID, chatSetting> with help of Iterator should be used method below
    // e.g.
    //    Iterator<Map.Entry<String, Integer>> iterator = dataHolder.entrySet().iterator();
    //    while (dataHolder.hasNext()) {
    //        Map.Entry<String, Integer> entry = iterator.next();
    //        System.out.println(entry.getKey() + ":" + entry.getValue())
    //        sendNotification(entry.getKey(), ?entry.getValue().getChatSetting.getTimeNotification);
    //    }


    public void sendNotification(String chatId, int time) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // String message = getInfo() method
                // SendMessage sendMessage = new SendMessage();
                //        sendMessage.setText(message);
                //        sendMessage.setChatId(chatId);
                //
                //        try {
                //            execute(sendMessage);
                //        } catch (TelegramApiException e) {
                //            e.printStackTrace();
                //        }
                System.out.println("//getInfo() result ");

            }
        }, c.getTime(), 86400000); // it is 24h

    }

//    private void sendText(String text){
//        String chatId;
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(text);
//        sendMessage.setChatId(chatId);
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//    }
}

class NotificationSenderTester {
    public static void main(String[] args) {
        //test below shoes that it works :)
        //console gave at first "the end", then "get info" at 12 h, then "get info"at 14" (test was done with minutes)
        NotificationSender ns = new NotificationSender();
        List<Integer> testList= Arrays.asList(14,12);

        for (Integer integer : testList) {
            ns.sendNotification("chat id", integer);
        }
        System.out.println("the end");
    }
}