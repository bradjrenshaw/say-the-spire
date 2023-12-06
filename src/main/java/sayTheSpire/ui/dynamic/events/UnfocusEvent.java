package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class UnfocusEvent extends DynamicEvent {

    public UnfocusEvent(DynamicElement target) {
        super("unfocus", target);
    }

}
