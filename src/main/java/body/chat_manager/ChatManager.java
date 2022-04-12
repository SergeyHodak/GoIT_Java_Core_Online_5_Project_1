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

    public ChatManager(ChatPlace chatPlace){
        chatSettings = new ChatSettings();
        this.chatPlace = chatPlace;
    }
    void setPlaceToStart()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToStart();
    }
    void  setPlaceToMainMenu()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToMainMenu();
    }
    void  setPlaceToGetInfo()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToGetInfo();
    }
    void  setPlaceToSettings()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToSettings();
    }
    void  setPlaceToQuantityOfDigitsAfterDot()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToQuantityOfDigitsAfterDot();
    }
    void  setPlaceToCurrencies()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToCurrencies();
    }
    void  setPlaceToBanks()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToBanks();
    }
    void  setPlaceToTimeOfNotification()throws OperationNotSupportedException{
        chatPlace = chatPlace.goToTimeOfNotification();
    }

    /**
     * This operation is not allowed. Such change is not protected.
     * @param chatPlace
     * @throws OperationNotSupportedException
     */
    private void setChatPlace(ChatPlace chatPlace) throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    @Override
    public String toString(){
        String result = "";
        result += ("Chat place = " + chatPlace.toString());
        result += ("\nChat settings:\n" + chatSettings.toString() + '\n');
        return result;
    }
}
