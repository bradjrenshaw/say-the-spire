package sayTheSpire.config;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import sayTheSpire.Output;
import sayTheSpire.localization.LocalizedStringIdentifier;
import sayTheSpire.speech.SpeechHandler;
import sayTheSpire.ui.UIRegistry;

public class SettingsManager {

    private SettingCategory root;

    public SettingsManager() {
        this.root = new SettingCategory(null, "");
        this.buildDefaults();
    }

    private void buildDefaults() {
        SettingCategory root = this.getRoot();

        SettingCategory combat = root.addCategory("combat");
        combat.addBoolean("block_text", true);
        combat.addBoolean("buff_debuff_text", true);
        combat.addBoolean("card_events", true);
        combat.addBoolean("orb_events", true);

        SettingCategory input = root.addCategory("input");
        // This shouldn't be changed mid-game and should only be changed by the user as a last resort
        input.addBoolean("virtual_input", true).setLocked(true);

        SettingCategory map = root.addCategory("map");
        map.addBoolean("read_reversed_paths", true);

        SettingCategory ui = root.addCategory("ui");
        ui.addBoolean("read_proceed_text", true);
        ui.addBoolean("read_banner_text", true);
        ui.addBoolean("read_positions", true);
        ui.addBoolean("read_types", true);
        ui.addBoolean("read_obtain_events", true);
        ui.addChoiceArray("exclude_read_typenames", new Supplier<Set<LocalizedStringIdentifier>>() {
            public Set<LocalizedStringIdentifier> get() {
                return UIRegistry.getRegisteredTypenameIdentifiers();
            }
        });

        SettingCategory advanced = root.addCategory("advanced");
        advanced.addBoolean("use_updated_card_description", false);
        advanced.addChoiceArray("prefered_speech_handler_order", new Supplier<Set<LocalizedStringIdentifier>>() {
            public Set<LocalizedStringIdentifier> get() {
                return Output.speechManager.getHandlers().stream()
                        .map(t -> new LocalizedStringIdentifier(t.getName(), t.getLabel())).collect(Collectors.toSet());
            }
        });
        advanced.addBoolean("speech_handler_force_system_speech", false);

        SettingCategory resources = root.addCategory("resources");
        resources.addBoolean("unload_native_libs", true);
        resources.addBoolean("dispose_resource_files", true);
    }

    public void fromJsonElement(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();

        // base refers to sayTheSpire; plugins will refer to plugin settings later
        this.getRoot().fromJsonElement(obj.get("base"));
    }

    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        JsonElement base = this.getRoot().toJsonElement();
        obj.add("base", base);
        return obj;
    }

    public Setting getBaseSetting(String path) {
        return this.getRoot().getSettingFromPath(path);
    }

    public SettingCategory getRoot() {
        return this.root;
    }

}
