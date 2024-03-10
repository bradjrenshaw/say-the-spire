package sayTheSpire.config;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import sayTheSpire.localization.LocalizedStringIdentifier;
import sayTheSpire.ui.dynamic.elements.DynamicElement;
import sayTheSpire.ui.dynamic.elements.settings.DynamicSettingsChoiceArrayButton;
import sayTheSpire.ui.dynamic.screens.Screen;

public class ChoiceArraySetting extends ArraySetting {

    private Supplier<Set<LocalizedStringIdentifier>> choicesFunc;

    public ChoiceArraySetting(SettingCategory parent, String name,
            Supplier<Set<LocalizedStringIdentifier>> choicesFunc) {
        this(parent, name, null, choicesFunc);
    }

    public ChoiceArraySetting(SettingCategory parent, String name, ArrayList<String> defaultValues,
            Supplier<Set<LocalizedStringIdentifier>> choicesFunc) {
        super(parent, name, defaultValues);
        this.choicesFunc = choicesFunc;
    }

    public void addValue(String value) {
        // This could be more efficient
        Map<String, String> choices = this.getChoices();
        if (!choices.containsKey(value)) {
            return;
        }
        super.addValue(value);
    }

    public DynamicElement getDynamicElement(Screen screen) {
        return new DynamicSettingsChoiceArrayButton(screen.getContext(), this);
    }

    public Map<String, String> getChoices() {
        return this.choicesFunc.get().stream()
                .collect(Collectors.toMap(LocalizedStringIdentifier::getKey, LocalizedStringIdentifier::getLocalized));
    }

}
