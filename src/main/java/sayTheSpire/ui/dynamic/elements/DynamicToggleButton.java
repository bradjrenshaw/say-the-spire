package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.Output;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.dynamic.events.ToggleChangeEvent;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.positions.Position;

public class DynamicToggleButton extends DynamicElement {

    private Boolean enabled;
    public SingleEventDispatcher<ClickEvent> click;
    public SingleEventDispatcher<ToggleChangeEvent> toggleChange;

    public DynamicToggleButton(ElementContainer parent, String label) {
        this(parent, label, null, false);
    }

    public DynamicToggleButton(ElementContainer parent, String label, Boolean enabled) {
        this(parent, label, null, false);
    }

    public DynamicToggleButton(ElementContainer parent, String label, String description) {
        this(parent, label, description, false);
    }

    public DynamicToggleButton(ElementContainer parent, String label, String description, Boolean enabled) {
        super(parent, "toggle button", label, description, null);
        this.setEnabled(enabled);
        this.click = new SingleEventDispatcher<>();
        this.toggleChange = new SingleEventDispatcher<>();
    }

    public void onToggle() {
        this.setEnabled(!this.getEnabled());
        Output.text(this.getStatusString(), false);
        this.click.dispatch(new ClickEvent(this));
        this.toggleChange.dispatch(new ToggleChangeEvent(this, this.getEnabled()));
    }

    public Boolean processInputJustPressed(InputAction action) {
        if (action.getName() == "select") {
            this.onToggle();
            return true;
        }
        return false;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public String getStatusString() {
        return this.localization.localize(this.getEnabled() ? "enabled" : "disabled");
    }

}
