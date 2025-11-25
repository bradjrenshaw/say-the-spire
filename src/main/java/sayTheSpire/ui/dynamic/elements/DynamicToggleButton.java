package sayTheSpire.ui.dynamic.elements;

import sayTheSpire.Output;
import sayTheSpire.ui.dynamic.events.ClickEvent;
import sayTheSpire.ui.dynamic.events.SingleEventDispatcher;
import sayTheSpire.ui.dynamic.events.ToggleChangeEvent;
import sayTheSpire.ui.input.InputAction;

public class DynamicToggleButton extends DynamicElement {

    private Boolean enabled;
    public SingleEventDispatcher<ClickEvent> click;
    public SingleEventDispatcher<ToggleChangeEvent> toggleChange;

    public DynamicToggleButton(String label) {
        this(label, null, false);
    }

    public DynamicToggleButton(String label, Boolean enabled) {
        this(label, null, false);
    }

    public DynamicToggleButton(String label, String description) {
        this(label, description, false);
    }

    public DynamicToggleButton(String label, String description, Boolean enabled) {
        super("toggle button", label, description, null);
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
        if (action.getKey() == "select") {
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
