package sayTheSpire.ui.dynamic.contexts;

import java.util.Stack;
import sayTheSpire.Output;
import sayTheSpire.ui.dynamic.screens.Screen;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.mod.Context;

public class UIContext extends Context {

    protected Stack<Screen> screens;

    public UIContext() {
        super();
        this.screens = new Stack();
    }

    public void onFocus() {
        if (this.screens.empty())
            return;
        Screen top = this.screens.peek();
        top.onFocus(false);
    }

    public void onUnfocus() {
        if (this.screens.empty())
            return;
        Screen top = this.screens.peek();
        top.onUnfocus(false);
    }

    public Screen popScreen() {
        if (this.screens.empty())
            return null;
        Screen top = this.screens.pop();
        top.onPop();
        if (!this.screens.empty())
            this.screens.peek().onFocus(false);
        return top;
    }

    public void pushScreen(Screen screen) {
        if (!this.screens.empty()) {
            Screen top = this.screens.peek();
            top.onUnfocus(false);
        }
        this.screens.add(screen);
        screen.onPush();
    }

    private void handleCancel() {
        if (!this.screens.empty()) {
            this.popScreen();
            if (this.screens.empty()) {
                Output.uiManager.popContext();
            }
        }
    }

    public Boolean onPress(InputAction action) {
        if (!this.screens.empty()) {
            Screen screen = this.screens.peek();
            if (screen.processInputPressed(action)) {
                return true;
            }
        }
        return action.isBaseGameAction();
    }

    public Boolean onJustPress(InputAction action) {
        if (!this.screens.empty()) {
            Screen screen = this.screens.peek();
            if (screen.processInputJustPressed(action)) {
                return true;
            }
        }
        if (action.getKey().equals("cancel")) {
            this.handleCancel();
            return true;
        }
        return action.isBaseGameAction();
    }

    public Boolean onRelease(InputAction action) {
        if (!this.screens.empty()) {
            Screen screen = this.screens.peek();
            if (screen.processInputReleased(action)) {
                return true;
            }
        }
        return action.isBaseGameAction();
    }

}
