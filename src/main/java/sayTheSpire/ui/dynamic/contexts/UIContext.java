package sayTheSpire.ui.dynamic.contexts;

import java.util.Stack;
import sayTheSpire.ui.mod.Context;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.dynamic.elements.ElementContainer;
import sayTheSpire.ui.Direction;

public class UIContext extends Context {

    protected Stack<ElementContainer> containerStack;

    public UIContext() {
        super();
        this.containerStack = new Stack();
    }

    public Boolean onPress(InputAction action) {
        if (this.containerStack.empty())
            return false;
        ElementContainer focus = this.containerStack.peek();
        return focus.processInputPressed(action);
    }

    public Boolean onJustPress(InputAction action) {
        if (this.containerStack.empty())
            return false;
        ElementContainer focus = this.containerStack.peek();
        return focus.processInputJustPressed(action);
    }

    public Boolean onRelease(InputAction action) {
        if (this.containerStack.empty())
            return false;
        ElementContainer focus = this.containerStack.peek();
        return focus.processInputReleased(action);
    }

}
