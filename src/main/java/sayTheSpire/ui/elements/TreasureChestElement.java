package sayTheSpire.ui.elements;

import com.megacrit.cardcrawl.rewards.chests.*;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;

public class TreasureChestElement extends UIElement {

    private AbstractChest chest;

    public TreasureChestElement(AbstractChest chest) {
        super("treasure chest");
        this.chest = chest;
    }

    public AbstractChest getChest() {
        return this.chest;
    }

    public String getLabel() {
        AbstractChest chest = this.getChest();
        if (chest instanceof SmallChest)
            return this.localization.localize("types.small");
        else if (chest instanceof MediumChest)
            return this.localization.localize("types.medium");
        else if (chest instanceof LargeChest)
            return this.localization.localize("types.large");
        else if (chest instanceof BossChest)
            return this.localization.localize("types.boss");
        return this.localization.localize("types.unknown");
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getLabel());
        return "UI";
    }

}
