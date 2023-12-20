package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.Direction;
import sayTheSpire.ui.input.InputAction;

public abstract class ElementContainer extends DynamicElement {

    ElementContainer(DynamicElement parent, String type, String label) {
        super(parent, type, label, null, null);
    }

    public abstract Boolean add(DynamicElement element);

    public abstract Position getChildPosition(DynamicElement element);

    public abstract DynamicElement getFocus();

    public Boolean processInputJustPressed(InputAction action) {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        return focus.processInputJustPressed(action);
    }

    public abstract Boolean remove(DynamicElement element);
}
