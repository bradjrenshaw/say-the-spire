package sayTheSpire.ui.dynamic.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sayTheSpire.Output;
import sayTheSpire.Output;
import sayTheSpire.config.KeyArraySetting;
import sayTheSpire.ui.IUIInfo;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.dynamic.events.ChoiceSelectEvent;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.EventHandler;

public class KeyArraySettingScreen extends Screen {

    private KeyArraySetting setting;
    private ListContainer valueContainer;

    public KeyArraySettingScreen(UIContext context, KeyArraySetting setting) {
        super(context);
        this.setting = setting;
    }

    public void addChoiceButton(IUIInfo item) {
        DynamicButton button = new DynamicButton(item.getLabel());
        button.setInfo(item);
        button.click.registerHandler(new EventHandler<ClickEvent>() {
            public Boolean execute(ClickEvent event) {
                if (setting.remove(item.getKey())) {
                    // todo localize this
                    Output.text(item.getLabel() + " removed", false);
                    valueContainer.remove(event.target);
                }
                return false;
            }
        });
        this.valueContainer.add(button);
    }

    public void setup() {
        ListContainer container = new ListContainer(this.setting.getLabel());

        // todo localize this
        this.valueContainer = new ListContainer("values");
        for (IUIInfo item : this.setting.getItems().values()) {
            this.addChoiceButton(item);
        }
        container.add(valueContainer);

        DynamicButton addButton = new DynamicButton("Add");
        addButton.click.registerHandler(new EventHandler<ClickEvent>() {
            public Boolean execute(ClickEvent event) {
                ChoiceSelectScreen selectScreen = new ChoiceSelectScreen(getContext(), setting.getChoicePrompt());
                for (IUIInfo choice : setting.getChoices().values()) {
                    selectScreen.addChoice(choice);
                }
                selectScreen.select.registerHandler(new EventHandler<ChoiceSelectEvent>() {
                    public Boolean execute(ChoiceSelectEvent event) {
                        closeChoicePopup(event.choice);
                        return false;
                    }
                });
                context.pushScreen(selectScreen);
                return false;
            }
        });
        container.add(addButton);

        this.setPrimaryContainer(container);
    }

    public void closeChoicePopup(IUIInfo choice) {
        // todo: Localize this
        this.setting.add(choice.getKey());
        this.addChoiceButton(choice);
        Output.text("Added " + choice.getLabel(), false);
        this.context.popScreen();
    }
}
