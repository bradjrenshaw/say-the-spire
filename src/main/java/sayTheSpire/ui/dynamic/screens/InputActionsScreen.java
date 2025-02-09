package sayTheSpire.ui.dynamic.screens;

import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.input.InputActionCollection;

//todo Localize this whole file
public class InputActionsScreen extends Screen {

    private InputActionCollection actions;

    public InputActionsScreen(UIContext context, InputActionCollection actions) {
        super(context);
        this.actions = actions;
    }

    public void setup() {
        ListContainer menu = new ListContainer("Input Actions", true);

        for (InputAction action : this.actions.getActions()) {
            ListContainer mappings = new ListContainer(action.getLabel(), false);
            menu.add(mappings);
        }

        this.setPrimaryContainer(menu);
    }
}
