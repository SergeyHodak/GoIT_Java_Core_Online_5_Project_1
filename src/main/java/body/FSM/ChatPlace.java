package body.FSM;

import javax.naming.OperationNotSupportedException;

public enum ChatPlace {
    MAIN_MENU {
        @Override
        ChatPlace goToSettings(){
            return ChatPlace.SETTINGS;
        }
        @Override
        ChatPlace goToMainMenu(){
            return ChatPlace.MAIN_MENU;
        }
    },
    SETTINGS {
        @Override
        ChatPlace goToMainMenu(){
            return ChatPlace.MAIN_MENU;
        }
        @Override
        ChatPlace goToBanks(){
            return ChatPlace.BANKS;
        }
        @Override
        ChatPlace goToCurrencies(){
            return ChatPlace.CURRENCIES;
        }
        @Override
        ChatPlace goToTimeOfNotification(){
            return ChatPlace.TIME_OF_NOTIFICATION;
        }
        @Override
        ChatPlace goToQuantityOfDigitsAfterDot(){
            return ChatPlace.QUANTITY_OF_DIGITS_AFTER_DOT;
        }
    },
    QUANTITY_OF_DIGITS_AFTER_DOT {
        @Override
        ChatPlace goToSettings(){
            return ChatPlace.SETTINGS;
        }
    },
    CURRENCIES {
        @Override
        ChatPlace goToSettings(){
            return ChatPlace.SETTINGS;
        }
    },
    BANKS {
        @Override
        ChatPlace goToSettings(){
            return ChatPlace.SETTINGS;
        }
    },
    TIME_OF_NOTIFICATION {
        @Override
        ChatPlace goToSettings(){
            return ChatPlace.SETTINGS;
        }
    };
    ChatPlace goToMainMenu()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    ChatPlace goToSettings()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    ChatPlace goToQuantityOfDigitsAfterDot()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    ChatPlace goToCurrencies()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    ChatPlace goToBanks()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
    ChatPlace goToTimeOfNotification()throws OperationNotSupportedException{
        throw new OperationNotSupportedException();
    }
}