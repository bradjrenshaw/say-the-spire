package sayTheSpire.ui;

import sayTheSpire.buffers.BufferManager;
import sayTheSpire.Output;

public class ToggleButtonElement extends UIElement {

    private Descriptor descriptor;
    private Boolean enabled;
    private String enabledText;
    private String disabledText;

    public ToggleButtonElement() {
        this("unknown", false);
    }

    public ToggleButtonElement(String name, Boolean enabled) {
        this(name, name, null, enabled);
    }

    public ToggleButtonElement(String name, String label, Boolean enabled) {
        this(name, label, null, enabled);
    }

    public ToggleButtonElement(String name, String label, String description, Boolean enabled) {
        this.elementType = "toggle button";
        this.descriptor = new Descriptor(name, label, description);
        this.enabled = enabled;
        this.setStatusTexts("on", "off");
    }

    public String handleBuffers(BufferManager buffers) {
        Output.setupUIBuffer(this.descriptor.getUIBufferContents());
        return null;
    }

    public Descriptor getDescriptor() {
        return this.descriptor;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getLabel() {
        return this.getDescriptor().getName();
    }

    public String getStatusString() {
        return this.getEnabled() ? this.enabledText : this.disabledText;
    }

    public void setStatusTexts(String enabled, String disabled) {
        this.enabledText = enabled;
        this.disabledText = disabled;
    }
}
