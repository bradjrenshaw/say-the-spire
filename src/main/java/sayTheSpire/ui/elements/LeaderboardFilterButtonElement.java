package sayTheSpire.ui.elements;

import java.util.ArrayList;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import sayTheSpire.ui.positions.Position;
import sayTheSpire.Output;
import sayTheSpire.buffers.BufferManager;

public class LeaderboardFilterButtonElement extends UIElement {

    private FilterButton button;
    private Position position;
    private Boolean prevActive;

    public LeaderboardFilterButtonElement(FilterButton button, Position position) {
        super("toggle button", position);
        this.button = button;
        this.prevActive = button.active;
    }

    public FilterButton getButton() {
        return this.button;
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBufferMany(this.getLabel());
        return null;
    }

    public void update() {
        FilterButton button = this.getButton();
        if (button.hb.justHovered) {
            Output.setUI(this);
        }
        Boolean currentActive = this.getEnabled();
        if (currentActive != prevActive) {
            if (button.hb.hovered)
                Output.text(this.getStatusString(), false);
            this.prevActive = currentActive;
        }
    }

    public Boolean getEnabled() {
        return this.getButton().active;
    }

    public String getLabel() {
        return this.getButton().label;
    }

    public String getStatusString() {
        return Output.localization.localize("ui.status." + (this.getEnabled() ? "selected" : "unselected"));
    }
}
