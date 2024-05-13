package sayTheSpire.ui.dynamic.events;

import sayTheSpire.ui.IUIInfo;
import sayTheSpire.ui.dynamic.elements.DynamicElement;

public class ChoiceSelectEvent extends DynamicEvent {

    public final int index;
    public final IUIInfo choice;

    public ChoiceSelectEvent(DynamicElement target, IUIInfo choice, int index) {
        super("choiceSelect", target);
        this.choice = choice;
        this.index = index;
    }

}
