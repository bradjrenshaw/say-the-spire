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

    private void processCancelInput() {
        if (this.containerStack.empty())
            return;
        ElementContainer container = this.containerStack.peek();
        container.processCancelInput();
    }

    private void processConfirmInput() {
        if (this.containerStack.empty())
            return;
        ElementContainer container = this.containerStack.peek();
        container.processConfirmInput();
    }

    private void processInputDirection(Direction direction) {
        if (this.containerStack.empty())
            return;
        ElementContainer container = this.containerStack.peek();
        container.processDirectionInput(direction);
    }

    public Boolean onJustPress(InputAction action) {
        switch (action.getName()) {
        case "confirm":
            this.processConfirmInput();
            return true;
        case "up":
        case "alt up":
            this.processInputDirection(Direction.UP);
            return true;
        case "down":
        case "alt down":
            this.processInputDirection(Direction.DOWN);
            return true;
        case "left":
        case "alt left":
            this.processInputDirection(Direction.LEFT);
            return true;
        case "right":
        case "alt right":
            this.processInputDirection(Direction.RIGHT);
            return true;
        }
        return true;
    }

}
