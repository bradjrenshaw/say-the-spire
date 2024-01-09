package sayTheSpire.ui.dynamic.screens;

import sayTheSpire.config.Setting;
import sayTheSpire.config.SettingCategory;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.ListContainer;

public class SettingsCategoryScreen extends Screen {

    private SettingCategory category;

    public SettingsCategoryScreen(UIContext context, SettingCategory category) {
        super(context);
        this.category = category;
    }

    public void setup() {
        ListContainer list = new ListContainer(this.category.getLabel());
        for (Setting setting : this.category.getSettings().values()) {
            list.add(setting.getDynamicElement(this));
        }
        this.setPrimaryContainer(list);
    }

}
