package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class ClickEvent extends DynamicEvent {

    public ClickEvent(DynamicElement target) {
        super("click", target);
    }
}
