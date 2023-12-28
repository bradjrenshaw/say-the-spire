package sayTheSpire.ui.dynamic.contexts;

import java.util.Stack;
import sayTheSpire.ui.mod.Context;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.dynamic.elements.ElementContainer;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.dynamic.screens.Screen;

public class UIContext extends Context {

    protected Stack<Screen> screens;

    public UIContext() {
        super();
        this.screens = new Stack();
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

    public Boolean onPress(InputAction action) {
        if (this.screens.empty())
            return false;
        Screen screen = this.screens.peek();
        return screen.processInputPressed(action) || action.isUIAction();
    }

    public Boolean onJustPress(InputAction action) {
        if (this.screens.empty())
            return false;
        Screen screen = this.screens.peek();
        return screen.processInputJustPressed(action) || action.isUIAction();
    }

    public Boolean onRelease(InputAction action) {
        if (this.screens.empty())
            return false;
        Screen screen = this.screens.peek();
        return screen.processInputReleased(action) || action.isUIAction();
    }

}
