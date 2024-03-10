package sayTheSpire.ui.dynamic.screens;

import java.util.ArrayList;

import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.dynamic.events.ChoiceSelectEvent;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.EventHandler;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;

public class ChoiceSelectScreen extends Screen {

    private String prompt;
    private ArrayList<String> choices;
    public final SingleEventDispatcher<ChoiceSelectEvent> select;

    public ChoiceSelectScreen(UIContext context, String prompt) {
        super(context);
        this.prompt = prompt;
        this.choices = new ArrayList<>();
        this.select = new SingleEventDispatcher<>();
    }

    public void addChoice(String text) {
        this.choices.add(text);
    }

    public void setup() {
        ListContainer choiceContainer = new ListContainer(this.prompt);

        int choiceCount = this.choices.size();
        for (int c = 0; c < choiceCount; c++) {
            String label = this.choices.get(c);
            int index = c;
            DynamicButton button = new DynamicButton(label);
            button.click.registerHandler(new EventHandler<ClickEvent>() {
                public Boolean execute(ClickEvent event) {
                    select.dispatch(new ChoiceSelectEvent(button, label, index));
                    return false;
                }
            });
        }
    }

}
