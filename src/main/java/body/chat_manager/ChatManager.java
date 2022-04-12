package body.chat_manager;

import lombok.Data;

import javax.naming.OperationNotSupportedException;

@Data
public class ChatManager {
    public final ChatSettings chatSettings; // Тут доцільно використати паблік файнал.
    // Всередині класу є свої геттери/сеттери.
    //Змінити щось як не передбачено ChatSettings з зовні неможливо
    //Змінити інстанс chatSettings з зовні неможливо
    private ChatPlace chatPlace;

    /**
     * ChatPlace modification will not affect real chat place. Use setter for it.
     * @return current chat place
     */
    public ChatPlace getChatPlace(){
        return chatPlace;
    }
    public ChatManager(){
        chatSettings = new ChatSettings();
        chatPlace = ChatPlace.START;
    }
}
