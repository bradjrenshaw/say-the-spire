package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class DynamicEvent {

    public String type;
    public DynamicElement target;

    DynamicEvent(String type, DynamicElement target) {
        this.type = type;
        this.target = target;
    }

}
