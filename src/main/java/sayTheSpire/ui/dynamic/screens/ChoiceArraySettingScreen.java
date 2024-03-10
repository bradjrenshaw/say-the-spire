package sayTheSpire.ui.dynamic.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sayTheSpire.Output;
import sayTheSpire.config.ChoiceArraySetting;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.elements.ListContainer;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.EventHandler;

public class ChoiceArraySettingScreen extends Screen {

    private ChoiceArraySetting setting;
    private ListContainer valueContainer;

    public ChoiceArraySettingScreen(UIContext context, ChoiceArraySetting setting) {
        super(context);
        this.setting = setting;
    }

    public void setup() {
        ListContainer container = new ListContainer(this.setting.getLabel());

        // todo localize this
        this.valueContainer = new ListContainer("values");
        ArrayList<String> settingValues = (ArrayList<String>) this.setting.getValue();
        Map<String, String> choices = this.setting.getChoices();
        for (String value : settingValues) {
            DynamicButton button = new DynamicButton(choices.get(value));
            button.click.registerHandler(new EventHandler<ClickEvent>() {
                public Boolean execute(ClickEvent event) {
                    if (setting.removeValue(event.target.getLabel())) {
                        // todo localize this
                        Output.text(value + " removed", false);
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
                List<String> settingValues = (List<String>) setting.getValue();

                return false;
            }
        });

        container.add(addButton);

        this.setPrimaryContainer(container);
    }
}
