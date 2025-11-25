package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.input.InputAction;

public class DynamicButton extends DynamicElement {

    public final SingleEventDispatcher<ClickEvent> click;

    public DynamicButton(String label) {
        this(label, null);
    }

    public DynamicButton(String label, String description) {
        super("button", label, description);
        this.click = new SingleEventDispatcher<>();
    }

    public void onClick() {
        this.click.dispatch(new ClickEvent(this));
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (action.getKey() == "select") {
            this.onClick();
            return true;
        }
        return false;
    }

}
