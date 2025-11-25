package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.Output;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.positions.Position;

public abstract class ElementContainer extends DynamicElement {

    public ElementContainer(String type, String label) {
        super(type, label, null, null);
    }

    public abstract Boolean add(DynamicElement element);

    public void enterFocus(Position position, Direction direction) {
    };

    public abstract Position getChildPosition(DynamicElement element);

    public abstract DynamicElement getFocus();

    public void onFocus(Boolean moved) {
        Output.text(this.getLabel(), false);
        if (!moved) {
            DynamicElement childFocus = this.getFocus();
            if (childFocus == null)
                return;
            childFocus.onFocus(false);
        }
    }

    public void onUnfocus(Boolean moved) {
        if (!moved) {
            DynamicElement childFocus = this.getFocus();
            if (childFocus == null)
                return;
            childFocus.onUnfocus(moved);
        }
    }

    public Boolean processInputJustPressed(InputAction action) {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        return focus.processInputJustPressed(action);
    }

    public abstract Boolean remove(DynamicElement element);
}
