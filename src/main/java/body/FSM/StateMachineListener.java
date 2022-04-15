package body.FSM;

import body.telegram.keyboardAction.KeyboardActions;

public interface StateMachineListener {
    KeyboardActions getKeyBoard();
    void onMessageReceived();
}