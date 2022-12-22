package sayTheSpire.ui.elements;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;

public class SortHeaderButtonElement extends UIElement {

    private SortHeaderButton button;
    private boolean isActive;
    private boolean isAscending;

    public SortHeaderButtonElement(SortHeaderButton button) {
        super("button");
        this.button = button;
        this.isActive = this.getIsActive();
        this.isAscending = this.getIsAscending();
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getLabel(), this.getStatusString());
        return null;
    }

    public void update() {
        boolean newIsActive = this.getIsActive();
        boolean newIsAscending = this.getIsAscending();
        if (newIsActive != this.isActive || newIsAscending != this.isAscending) {
            this.isActive = newIsActive;
            this.isAscending = newIsAscending;
            if (Output.currentUI == this)
                Output.text(this.getStatusString(), false);
        }
    }

    public boolean getIsActive() {
        return (boolean) ReflectionHacks.getPrivate(this.button, SortHeaderButton.class, "isActive");
    }

    public boolean getIsAscending() {
        return (boolean) ReflectionHacks.getPrivate(this.button, SortHeaderButton.class, "isAscending");
    }

    public String getLabel() {
        String name = (String) ReflectionHacks.getPrivate(this.button, SortHeaderButton.class, "text");
        return name;
    }

    public String getStatusString() {
        String status;
        if (!this.isActive)
            status = this.localization.localize(".ui.status.disabled");
        else
            status = this.isAscending ? "ascending" : "descending";
        return this.localization.localize(".ui.status." + status);
    }
}
