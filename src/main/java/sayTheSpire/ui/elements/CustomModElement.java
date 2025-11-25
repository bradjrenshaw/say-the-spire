package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import java.util.HashSet;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.localization.LocalizationContext;

public class CustomModElement extends UIElement {

    private String label;
    private CustomMod mod;
    private HashSet<CustomMod> mutuallyExclusive;
    private LocalizationContext localization;

    public CustomModElement(CustomMod mod) {
        super("toggle button");
        this.mod = mod;
        this.label = (String) ReflectionHacks.getPrivate(mod, CustomMod.class, "label");
        this.mutuallyExclusive = (HashSet<CustomMod>) ReflectionHacks.getPrivate(mod, CustomMod.class,
                "mutuallyExclusive");
        this.localization = Output.localization.getContext("ui.elements.CustomMod");
    }

    public String handleBuffers(BufferManager buffers) {
        StringBuilder sb = new StringBuilder();
        sb.append("Excludes: ");
        if (this.mutuallyExclusive != null && !this.mutuallyExclusive.isEmpty()) {
            for (CustomMod other : this.mutuallyExclusive) {
                sb.append(other.name + "\n");
            }
        } else {
            sb.append("none");
        }
        Output.setupUIBufferMany(this.mod.name, this.mod.description, sb.toString());
        return null;
    }

    public void onToggle() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getStatusString());
        if (this.mod.selected && this.mutuallyExclusive != null && !this.mutuallyExclusive.isEmpty()) {
            sb.append("\n");
            for (CustomMod other : this.mutuallyExclusive) {
                this.localization.put("target", other.name);
                sb.append(this.localization.localize("disabledOther"));
            }
        }
        Output.text(sb.toString(), true);
    }

    public String getLabel() {
        return TextParser.parse(this.label);
    }

    public String getStatusString() {
        return this.mod.selected ? this.localization.localize(".ui.status.selected")
                : this.localization.localize(".ui.status.unselected");
    }
}
