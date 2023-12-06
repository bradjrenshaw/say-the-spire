package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class FocusEvent extends DynamicEvent {

    public FocusEvent(DynamicElement target) {
        super("focus", target);
    }
}
