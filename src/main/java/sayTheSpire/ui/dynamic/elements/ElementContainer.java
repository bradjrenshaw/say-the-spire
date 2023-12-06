package sayTheSpire.ui.dynamic.elements;

import java.util.List;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.ui.Direction;

public abstract class ElementContainer extends DynamicElement {

    ElementContainer(DynamicElement parent, String type) {
        super(parent, type);
    }

    public abstract Boolean add(DynamicElement element);

    public abstract Position getElementPosition(DynamicElement element);

    public abstract DynamicElement getFocus();

    public abstract Boolean processDirectionInput(Direction direction);

    public abstract Boolean remove(DynamicElement element);
}
