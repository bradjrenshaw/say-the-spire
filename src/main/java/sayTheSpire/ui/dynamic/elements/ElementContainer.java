package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.Direction;

public abstract class ElementContainer extends DynamicElement {

    ElementContainer(DynamicElement parent, String type, String label) {
        super(parent, type, label, null, null);
    }

    public abstract Boolean add(DynamicElement element);

    public abstract Position getChildPosition(DynamicElement element);

    public abstract DynamicElement getFocus();

    public Boolean processCancelInput() {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        return focus.processCancelInput();
    }

    public Boolean processConfirmInput() {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        return focus.processConfirmInput();
    }

    public Boolean processDirectionInput(Direction direction) {
        DynamicElement focus = this.getFocus();
        if (focus == null)
            return false;
        return focus.processDirectionInput(direction);
    }

    public abstract Boolean remove(DynamicElement element);
}
