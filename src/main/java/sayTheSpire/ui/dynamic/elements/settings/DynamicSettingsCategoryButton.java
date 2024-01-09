package sayTheSpire.ui.dynamic.elements.settings;

import sayTheSpire.config.SettingCategory;
import sayTheSpire.ui.dynamic.contexts.UIContext;
import sayTheSpire.ui.dynamic.elements.DynamicButton;
import sayTheSpire.ui.dynamic.screens.SettingsCategoryScreen;

public class DynamicSettingsCategoryButton extends DynamicButton {

    private SettingCategory category;
    private UIContext context;

    public DynamicSettingsCategoryButton(UIContext context, SettingCategory category) {
        super(null);
        this.context = context;
        this.category = category;
    }

    public void onClick() {
        super.onClick();
        this.context.pushScreen(new SettingsCategoryScreen(this.context, this.category));
    }

    public String getDescription() {
        return this.category.getDescription();
    }

    public String getLabel() {
        return this.category.getLabel();
    }

}
