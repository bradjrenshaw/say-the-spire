package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class ChoiceSelectEvent extends DynamicEvent {

    public final int index;
    public final String text;

    public ChoiceSelectEvent(DynamicElement target, String text, int index) {
        super("choiceSelect", target);
        this.text = text;
        this.index = index;
    }

}
