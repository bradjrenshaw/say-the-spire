package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class ToggleChangeEvent extends DynamicEvent {

    public Boolean value;

    public ToggleChangeEvent(DynamicElement target, Boolean value) {
        super("toggleChange", target);
        this.value = value;
    }

}
