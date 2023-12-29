package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.input.InputAction;;

public class DynamicButton extends DynamicElement {

    public final SingleEventDispatcher<ClickEvent> click;

    public DynamicButton(ElementContainer parent, String label, String description) {
        super(parent, "button", label, description);
        this.click = new SingleEventDispatcher<>();
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (action.getName() == "confirm") {
            this.click.dispatch(new ClickEvent(this));
            return true;
        }
        return false;
    }

}
