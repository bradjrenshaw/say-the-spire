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
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.EventHandler;

public class KeyArraySettingScreen extends Screen {

    private KeyArraySetting setting;
    private ListContainer valueContainer;

    public KeyArraySettingScreen(UIContext context, KeyArraySetting setting) {
        super(context);
        this.setting = setting;
    }

    public void setup() {
        ListContainer container = new ListContainer(this.setting.getLabel());

        // todo localize this
        this.valueContainer = new ListContainer("values");
        for (IUIInfo item : this.setting.getItems().values()) {
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
        container.add(valueContainer);

        DynamicButton addButton = new DynamicButton("Add");
        addButton.click.registerHandler(new EventHandler<ClickEvent>() {
            public Boolean execute(ClickEvent event) {
                ChoiceSelectScreen selectScreen = new ChoiceSelectScreen(getContext(), "Select typename to exclude");
                for (IUIInfo choice : setting.getChoices().values()) {
                    selectScreen.addChoice(choice);
                }
                context.pushScreen(selectScreen);
                return false;
            }
        });

        container.add(addButton);

        this.setPrimaryContainer(container);
    }
}
