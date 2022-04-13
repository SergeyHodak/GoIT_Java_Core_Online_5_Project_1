package body.chat_manager;

import javax.naming.OperationNotSupportedException;

public class ChatManagerTests {
    public static void main(String[] args) {
        ChatManager chatManager = new ChatManager(ChatPlace.START);//На приклад, створився новий чат - створюємо для нього чат менеджер
        System.out.println("chatManager.toString() = " + chatManager.toString());
        chatManager.chatSettings.setBank("privatBank");//у публічному полі "настройки чату" викликаємо метод щоб задати значення "банк"
        try {                                                                   //Викликаємо метод який переведе чат у друге місце
            chatManager.setPlaceToMainMenu();                                   //якщо хтось нажав кнопку старт(перехід у головне меню)
        } catch (OperationNotSupportedException e){                             //Це робиться у блоці трай-кетч щоб ми могли відслідкувати недопустимий перехід
            System.out.println("e.getStackTrace() = " + e.getStackTrace());     //наприклад з головного меню в старт потрапити неможливо
        }                                                                       //
        System.out.println("chatManager.toString( expected privat and main menu) = " + chatManager.toString());
        /**
        * try {
        *     chatManager.setPlaceToStart();
        * } catch (OperationNotSupportedException e){
        *     System.out.println("Impossible to set position such way");
        *     //Але якщо ми пробуємо зробити недопустимий перехід то джава кидає нам ексепшин, отже помилитись з
        *     //недопустимим переходом неможливо
        * }
        * // Від непередбачуваної зміни позиції код захищено, це не спрацює
        * System.out.println("chatManager.toString(expected privat and main menu) = " + chatManager.toString());
        * try {
        *     chatManager.setChatPlace(ChatPlace.START);
        * } catch (OperationNotSupportedException e){
        *     System.out.println("Impossible to set position such way");//Але якщо ми пробуємо зробити недопустимий
        *     //перехід то джава кидає нам ексепшин, отже помилитись з недопустимим переходом неможливо
        * }
        */
    }
}