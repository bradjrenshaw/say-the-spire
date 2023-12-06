package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class TextChangeEvent extends DynamicEvent {

    public String value;

    public TextChangeEvent(DynamicElement target, String value) {
        super("textChange", target);
        this.value = value;
    }

}
